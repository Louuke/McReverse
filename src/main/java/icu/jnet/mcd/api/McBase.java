package icu.jnet.mcd.api;

import com.google.api.client.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import icu.jnet.mcd.api.builder.HttpBuilder;
import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.api.request.Request;
import icu.jnet.mcd.api.response.status.Status;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.utils.OfferAdapterFactory;
import icu.jnet.mcd.utils.SensorCache;
import icu.jnet.mcd.utils.UserInfo;
import icu.jnet.mcd.utils.listener.Action;
import icu.jnet.mcd.utils.listener.ClientActionModel;
import icu.jnet.mcd.utils.listener.ClientStateListener;
import icu.jnet.mcd.network.RequestManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class McBase {

    private static final RequestManager reqManager = RequestManager.getInstance();
    private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OfferAdapterFactory()).create();
    private final transient ClientActionModel actionModel = new ClientActionModel();
    private final transient SensorCache cache = new SensorCache(actionModel);
    private final UserInfo userInfo = new UserInfo();
    private Authorization authorization = new Authorization();

    <T extends Response> T query(Request request, Class<T> responseType, String method) {
        HttpRequest httpRequest = new HttpBuilder(request, method, getEmail(), actionModel)
                .setReadTimeout(request.getReadTimeout())
                .setAuthorization(getJWTToken(request))
                .setSensorToken(request.isTokenRequired() ? cache.getSensorToken() : null)
                .build();
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

    private String getJWTToken(Request mcdRequest) {
        return switch (mcdRequest.getAuthType()) {
            case Basic -> "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";
            case BasicBearer -> "Bearer " + actionModel.notifyListener(Action.BASIC_BEARER_REQUIRED, String.class);
            case Bearer -> "Bearer " + authorization.getAccessToken();
        };
    }

    private <T extends Response> T createInstance(Class<T> clazz, Status status) {
        try {
            return clazz.getConstructor(Status.class).newInstance(status);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
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

    ClientActionModel getActionModel() {
        return actionModel;
    }
}
