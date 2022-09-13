package icu.jnet.mcd.api;

import com.google.api.client.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import icu.jnet.mcd.api.builder.HttpBuilder;
import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.api.request.BasicBearerRequest;
import icu.jnet.mcd.api.request.RefreshRequest;
import icu.jnet.mcd.api.request.Request;
import icu.jnet.mcd.api.response.BasicBearerResponse;
import icu.jnet.mcd.api.response.LoginResponse;
import icu.jnet.mcd.api.response.status.Status;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.utils.OfferAdapterFactory;
import icu.jnet.mcd.utils.SensorCache;
import icu.jnet.mcd.utils.UserInfo;
import icu.jnet.mcd.utils.Utils;
import icu.jnet.mcd.utils.listener.Action;
import icu.jnet.mcd.utils.listener.ClientActionModel;
import icu.jnet.mcd.utils.listener.ClientStateListener;
import icu.jnet.mcd.network.RequestManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class McBase implements ClientStateListener {

    private static final RequestManager reqManager = RequestManager.getInstance();
    private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OfferAdapterFactory()).create();
    private final transient ClientActionModel actionModel = new ClientActionModel();
    private final transient SensorCache cache = new SensorCache(actionModel);
    private final UserInfo userInfo = new UserInfo();
    private Authorization authorization = new Authorization();

    public McBase() {
        actionModel.addStateListener(this);
    }

    <T extends Response> T query(Request request, Class<T> responseType, String method) {
        HttpBuilder builder = configureBuilder(request, method);
        waitForUnlock();
        HttpRequest httpRequest = builder.build();
        return execute(httpRequest, responseType);
    }

    private <T extends Response> T execute(HttpRequest request, Class<T> clazz) {
        try {
            reqManager.enqueue(request);
            HttpResponse response = request.execute();
            if(response.isSuccessStatusCode()) {
                return gson.fromJson(response.parseAsString(), clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz, new Status());
    }

    private HttpBuilder configureBuilder(Request request, String method) {
        return new HttpBuilder(request, method, getEmail(), actionModel)
                .setReadTimeout(request.getReadTimeout())
                .setAuthorization(authorization)
                .setSensorToken(request.isTokenRequired() ? cache.getSensorToken() : null);
    }

    @Override
    public String basicBearerRequired() {
        return query(new BasicBearerRequest(), BasicBearerResponse.class, HttpMethods.POST).getToken();
    }

    @Override
    public Authorization jwtExpired() {
        userInfo.setLocked(true);
        LoginResponse login = query(new RefreshRequest(getAuthorization().getRefreshToken()), LoginResponse.class, HttpMethods.POST);
        if(login.success()) {
            setAuthorization(login.getResponse());
            actionModel.notifyListener(Action.AUTHORIZATION_CHANGED);
            return getAuthorization();
        } else {
            actionModel.notifyListener(Action.JWT_INVALID);
        }
        userInfo.setLocked(false);
        return login.getResponse();
    }

    private <T extends Response> T createInstance(Class<T> clazz, Status status) {
        try {
            return clazz.getConstructor(Status.class).newInstance(status);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void waitForUnlock() {
        for(int i = 0; i < 3000 && userInfo.isLocked(); i += 100) {
            Utils.waitMill(100);
        }
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getEmail() {
        return userInfo.getEmail();
    }

    public void addStateListener(ClientStateListener listener) {
        actionModel.addStateListener(listener);
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
}
