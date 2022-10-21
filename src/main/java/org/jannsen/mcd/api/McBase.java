package org.jannsen.mcd.api;

import com.google.api.client.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.jannsen.mcd.api.builder.HttpBuilder;
import org.jannsen.mcd.api.entity.login.Authorization;
import org.jannsen.mcd.api.request.BasicBearerRequest;
import org.jannsen.mcd.api.request.RefreshRequest;
import org.jannsen.mcd.api.request.Request;
import org.jannsen.mcd.api.response.BasicBearerResponse;
import org.jannsen.mcd.api.response.LoginResponse;
import org.jannsen.mcd.api.response.adapter.CodeAdapter;
import org.jannsen.mcd.api.response.status.Status;
import org.jannsen.mcd.api.response.Response;
import org.jannsen.mcd.network.RefreshManager;
import org.jannsen.mcd.api.response.adapter.OfferAdapter;
import org.jannsen.mcd.utils.SensorCache;
import org.jannsen.mcd.utils.UserInfo;
import org.jannsen.mcd.constants.Action;
import org.jannsen.mcd.utils.listener.ClientActionModel;
import org.jannsen.mcd.utils.listener.ClientStateListener;
import org.jannsen.mcd.network.RequestManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Proxy;
import java.net.SocketTimeoutException;

public class McBase implements ClientStateListener {

    private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OfferAdapter())
            .registerTypeAdapterFactory(new CodeAdapter()).create();
    private static final RequestManager requestManager = RequestManager.getInstance();
    private static final RefreshManager refreshManager = RefreshManager.getInstance();
    private final transient ClientActionModel actionModel = new ClientActionModel();
    private final transient SensorCache cache = new SensorCache(actionModel);
    private final Authorization authorization = new Authorization();
    private final UserInfo userInfo = new UserInfo();
    private transient Proxy proxy;

    public McBase() {
        actionModel.addStateListener(this);
    }

    <T extends Response> T query(Request request, Class<T> responseType, String method) {
        HttpBuilder builder = configureBuilder(request, method);
        HttpRequest httpRequest = builder.build();
        return execute(httpRequest, responseType);
    }

    private <T extends Response> T execute(HttpRequest request, Class<T> clazz) {
        try {
            requestManager.enqueue(request);
            HttpResponse httpResponse = request.execute();
            String content = httpResponse.parseAsString();
            //System.out.println(content);
            if(httpResponse.isSuccessStatusCode()) {
                T response = gson.fromJson(content, clazz);
                handleErrorResponse(response);
                return response;
            }
            return createErrorResponse(clazz, content);
        } catch (SocketTimeoutException e) {
            actionModel.notifyListener(Action.REQUEST_TIMED_OUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createErrorResponse(clazz, "");
    }

    private <T extends Response> T createErrorResponse(Class<T> clazz, String error) {
        try {
            Response response = gson.fromJson(error, Response.class);
            return clazz.getConstructor(Status.class).newInstance(response != null ? response.getStatus() : new Status());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            System.err.println("JSON error: " + e.getMessage());
            return createErrorResponse(clazz, "");
        }
    }

    private <T extends Response> void handleErrorResponse(T response) {
        if(response != null && !response.getStatus().getErrors().isEmpty()) {
            switch (response.getStatus().getErrors().get(0).getErrorCode()) {
                case 11310, 41471 -> actionModel.notifyListener(Action.ACCOUNT_DELETED);
            }
        }
    }

    private HttpBuilder configureBuilder(Request request, String method) {
        return new HttpBuilder(request, method, proxy, actionModel)
                .setAuthorization(authorization)
                .setSensorToken(request.isTokenRequired() ? cache.getSensorToken() : null);
    }

    @Override
    public String basicBearerRequired() {
        return query(new BasicBearerRequest(), BasicBearerResponse.class, HttpMethods.POST).getToken();
    }

    @Override
    public Authorization jwtExpired() {
        refreshManager.waitForLock();
        if(!refreshManager.isNewerAuthCached(userInfo, authorization)) {
            LoginResponse login = refreshAuthorization();
            if(login.success()) {
                setAuthorization(login.getResponse());
                actionModel.notifyListener(Action.AUTHORIZATION_CHANGED);
                refreshManager.saveAuthorization(userInfo, authorization);
            }
        } else {
            setAuthorization(refreshManager.getCachedAuthorization(userInfo));
        }
        refreshManager.unlock();
        return authorization;
    }

    public LoginResponse refreshAuthorization() {
        return query(new RefreshRequest(authorization.getRefreshToken()), LoginResponse.class, HttpMethods.POST);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getEmail() {
        return userInfo.getEmail();
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization.setAccessToken(authorization.getAccessToken());
        this.authorization.setRefreshToken(authorization.getRefreshToken());
        this.authorization.setCreatedUnix(authorization.getCreatedUnix());
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void addStateListener(ClientStateListener listener) {
        actionModel.addStateListener(listener);
    }
}
