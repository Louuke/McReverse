package org.jannsen.mcreverse.api;

import com.google.api.client.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.jannsen.mcreverse.api.request.builder.AuthProvider;
import org.jannsen.mcreverse.api.request.builder.HttpBuilder;
import org.jannsen.mcreverse.api.entity.login.BasicBearerAuthorization;
import org.jannsen.mcreverse.api.entity.login.BearerAuthorization;
import org.jannsen.mcreverse.api.exception.HttpResponseHandler;
import org.jannsen.mcreverse.api.request.BasicBearerRequest;
import org.jannsen.mcreverse.api.request.RefreshRequest;
import org.jannsen.mcreverse.api.request.Request;
import org.jannsen.mcreverse.api.request.builder.TokenProvider;
import org.jannsen.mcreverse.api.response.BasicBearerResponse;
import org.jannsen.mcreverse.api.response.LoginResponse;
import org.jannsen.mcreverse.api.response.adapter.CodeAdapter;
import org.jannsen.mcreverse.api.response.status.Status;
import org.jannsen.mcreverse.api.response.Response;
import org.jannsen.mcreverse.api.response.adapter.OfferAdapter;
import org.jannsen.mcreverse.utils.UserInfo;
import org.jannsen.mcreverse.constants.Action;
import org.jannsen.mcreverse.utils.listener.ClientActionNotifier;
import org.jannsen.mcreverse.utils.listener.ClientActionListener;
import org.jannsen.mcreverse.network.RequestScheduler;

import java.lang.reflect.InvocationTargetException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.Objects;

public class McBase implements ClientActionListener {

    private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OfferAdapter())
            .registerTypeAdapterFactory(new CodeAdapter()).create();
    private static final RequestScheduler requestScheduler = RequestScheduler.getInstance();
    private BearerAuthorization authorization = new BearerAuthorization();
    private final UserInfo userInfo = new UserInfo();
    private final transient ClientActionNotifier clientAction = new ClientActionNotifier(this);
    private final transient AuthProvider authProvider = new AuthProvider(userInfo, () -> authorization, this::requestBasicBearer);
    private final transient TokenProvider tokenProvider = new TokenProvider(clientAction);
    private transient Proxy proxy;

    <T extends Response> T query(Request request, Class<T> responseType, String httpMethod) {
        return execute(configureBuilder(request, httpMethod).build(), responseType);
    }

    private <T extends Response> T execute(HttpRequest request, Class<T> clazz) {
        try {
            requestScheduler.enqueue(request);
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
            clientAction.notifyListener(Action.REQUEST_TIMED_OUT);
        } catch (Exception e) {
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
                case 11310, 41471 -> clientAction.notifyListener(Action.ACCOUNT_DELETED);
            }
        }
    }

    private HttpBuilder configureBuilder(Request request, String httpMethod) {
        return new HttpBuilder()
                .setMcDRequest(request)
                .setHttpMethod(httpMethod)
                .setProxy(proxy)
                .setAuthorization(authProvider.getAppropriateAuth(request))
                .setUnsuccessfulResponseHandler(new HttpResponseHandler(clientAction))
                .setSensorToken(request.isTokenRequired() ? tokenProvider.getSensorToken(userInfo) : null);
    }

    @Override
    public BearerAuthorization jwtExpired() {
        setAuthorization(authProvider.scheduleRefresh(() -> refreshAuthorization().getResponse()));
        clientAction.notifyListener(Action.AUTHORIZATION_CHANGED);
        return authorization;
    }

    private BasicBearerAuthorization requestBasicBearer() {
        return query(new BasicBearerRequest(), BasicBearerResponse.class, HttpMethods.POST).getResponse();
    }

    private LoginResponse refreshAuthorization() {
        return query(new RefreshRequest(authorization.getRefreshToken()), LoginResponse.class, HttpMethods.POST);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getEmail() {
        return userInfo.getEmail();
    }

    public BearerAuthorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(BearerAuthorization authorization) {
        this.authorization = authorization;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void addActionListener(ClientActionListener listener) {
        clientAction.addListener(listener);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof McBase base)) {
            return false;
        }
        return base.getEmail().equals(getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmail());
    }
}
