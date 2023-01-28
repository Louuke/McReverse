package org.jannsen.mcreverse.api;

import com.google.api.client.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jannsen.mcreverse.api.entity.akamai.SensorToken;
import org.jannsen.mcreverse.api.exception.ExceptionHandler;
import org.jannsen.mcreverse.api.exception.HttpRetryHandler;
import org.jannsen.mcreverse.api.request.builder.AuthProvider;
import org.jannsen.mcreverse.api.request.builder.HttpBuilder;
import org.jannsen.mcreverse.api.entity.auth.BasicBearerAuthorization;
import org.jannsen.mcreverse.api.entity.auth.BearerAuthorization;
import org.jannsen.mcreverse.api.request.BasicBearerRequest;
import org.jannsen.mcreverse.api.request.Request;
import org.jannsen.mcreverse.api.request.builder.TokenProvider;
import org.jannsen.mcreverse.api.response.BasicBearerResponse;
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
import java.util.Optional;
import java.util.function.Supplier;

public class McBase {

    private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OfferAdapter())
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


    <T extends Response> T query(Request request, Class<T> responseType, String httpMethod) {
        HttpRequest httpRequest = configureBuilder(request, httpMethod).build();
        return execute(httpRequest, responseType);
    }

    private <T extends Response> T execute(HttpRequest request, Class<T> responseType) {
        Optional<String> responseContent = requestScheduler.enqueue(request::execute);
        return responseContent.filter(exceptionHandler::validJsonResponse)
                .map(content -> gson.fromJson(content, responseType))
                .orElse(exceptionHandler.createFallbackResponse(responseType));
    }

    private HttpBuilder configureBuilder(Request request, String httpMethod) {
        return new HttpBuilder()
                .setMcDRequest(request)
                .setHttpMethod(httpMethod)
                .setProxy(proxy)
                .setAuthorization(authProvider.getAppropriateAuth(request))
                .setUnsuccessfulResponseHandler(new HttpRetryHandler(this::getAuthorization, exceptionHandler::searchResponseError))
                .setSensorToken(request.isTokenRequired() ? tokenProvider.getSensorToken(email) : null);
    }

    private BasicBearerAuthorization requestBasicBearer() {
        return query(new BasicBearerRequest(), BasicBearerResponse.class, HttpMethods.POST).getResponse();
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
