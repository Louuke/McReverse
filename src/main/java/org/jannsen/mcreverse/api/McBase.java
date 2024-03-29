package org.jannsen.mcreverse.api;

import com.google.api.client.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jannsen.mcreverse.api.entity.akamai.SensorToken;
import org.jannsen.mcreverse.api.exception.ExceptionHandler;
import org.jannsen.mcreverse.api.exception.HttpRetryHandler;
import org.jannsen.mcreverse.api.request.RefreshRequest;
import org.jannsen.mcreverse.api.request.StreamRequest;
import org.jannsen.mcreverse.api.request.builder.AuthProvider;
import org.jannsen.mcreverse.api.request.builder.HttpBuilder;
import org.jannsen.mcreverse.api.entity.auth.BasicBearerAuthorization;
import org.jannsen.mcreverse.api.entity.auth.BearerAuthorization;
import org.jannsen.mcreverse.api.request.BasicBearerRequest;
import org.jannsen.mcreverse.api.request.Request;
import org.jannsen.mcreverse.api.request.builder.TokenProvider;
import org.jannsen.mcreverse.api.response.BasicBearerResponse;
import org.jannsen.mcreverse.api.response.LoginResponse;
import org.jannsen.mcreverse.api.response.adapter.CodeAdapter;
import org.jannsen.mcreverse.api.response.Response;
import org.jannsen.mcreverse.api.response.adapter.OfferAdapter;
import org.jannsen.mcreverse.utils.listener.ClientActionNotifier;
import org.jannsen.mcreverse.utils.listener.ClientActionListener;
import org.jannsen.mcreverse.network.RequestScheduler;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.net.Proxy;
import java.util.Objects;
import java.util.function.Supplier;

public class McBase implements ClientActionListener {

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping()
            .registerTypeAdapterFactory(new OfferAdapter())
            .registerTypeAdapterFactory(new CodeAdapter()).create();
    private static final RequestScheduler requestScheduler = RequestScheduler.getInstance();
    @Id
    private String email;
    private BearerAuthorization authorization = new BearerAuthorization();
    @Transient
    private final transient ClientActionNotifier clientAction = new ClientActionNotifier();
    @Transient
    private final transient ExceptionHandler exceptionHandler = new ExceptionHandler(clientAction::notifyListener);
    @Transient
    private final transient AuthProvider authProvider = new AuthProvider(this::requestBasicBearer, this::getAuthorization);
    @Transient
    private final transient TokenProvider tokenProvider = new TokenProvider();
    @Transient
    private transient Proxy proxy;

    <T extends Response> T query(StreamRequest request, String httpMethod, Class<T> responseType) {
        return requestScheduler.enqueueGetAsStream(buildRequest(request, httpMethod))
                .map(gson::toJson)
                .map(content -> gson.fromJson(content, responseType))
                .orElse(exceptionHandler.createFallbackResponse(responseType));
    }

    <T extends Response> T query(Request request, String httpMethod, Class<T> responseType) {
        return requestScheduler.enqueueGetString(buildRequest(request, httpMethod))
                .filter(exceptionHandler::validJsonResponse)
                .map(content -> gson.fromJson(content, responseType))
                //.map(exceptionHandler::searchError)
                .orElse(exceptionHandler.createFallbackResponse(responseType));
    }

    private HttpRequest buildRequest(Request request, String httpMethod) {
        return new HttpBuilder()
                .setMcDRequest(request)
                .setHttpMethod(httpMethod)
                .setProxy(proxy)
                .setAuthorization(authProvider.getAppropriateAuth(request))
                .setUnsuccessfulResponseHandler(new HttpRetryHandler(() -> authProvider.getAppropriateAuth(request), this::refreshAuthorization))
                .setSensorToken(request.isTokenRequired() ? tokenProvider.getSensorToken(email) : null)
                .build();
    }

    private void refreshAuthorization() {
        if(getAuthorization().getAccessToken().isEmpty()) return;
        LoginResponse response = query(new RefreshRequest(getAuthorization().getRefreshToken()), HttpMethods.POST, LoginResponse.class);
        if(response.success()) setAuthorization(response.getResponse());
    }

    private BasicBearerAuthorization requestBasicBearer() {
        return query(new BasicBearerRequest(), HttpMethods.POST, BasicBearerResponse.class).getResponse();
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAuthorization(BearerAuthorization authorization) {
        this.authorization = authorization;
        clientAction.notifyAuthorizationChanged(authorization);
    }

    public BearerAuthorization getAuthorization() {
        return authorization;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void addActionListener(ClientActionListener listener) {
        clientAction.addListener(listener);
    }

    public void setTokenSupplier(Supplier<SensorToken> tokenSupplier) {
        tokenProvider.setTokenSupplier(tokenSupplier);
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
