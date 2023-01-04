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
import org.jannsen.mcreverse.api.request.RefreshRequest;
import org.jannsen.mcreverse.api.request.Request;
import org.jannsen.mcreverse.api.request.builder.TokenProvider;
import org.jannsen.mcreverse.api.response.BasicBearerResponse;
import org.jannsen.mcreverse.api.response.LoginResponse;
import org.jannsen.mcreverse.api.response.adapter.CodeAdapter;
import org.jannsen.mcreverse.api.response.Response;
import org.jannsen.mcreverse.api.response.adapter.OfferAdapter;
import org.jannsen.mcreverse.utils.UserInfo;
import org.jannsen.mcreverse.constants.Action;
import org.jannsen.mcreverse.utils.listener.ClientActionNotifier;
import org.jannsen.mcreverse.utils.listener.ClientActionListener;
import org.jannsen.mcreverse.network.RequestScheduler;

import java.net.Proxy;
import java.util.Objects;
import java.util.function.Supplier;

public class McBase {

    private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OfferAdapter())
            .registerTypeAdapterFactory(new CodeAdapter()).create();
    private static final RequestScheduler requestScheduler = RequestScheduler.getInstance();
    private BearerAuthorization authorization = new BearerAuthorization();
    private final UserInfo userInfo = new UserInfo();
    private final transient ClientActionNotifier clientAction = new ClientActionNotifier();
    private final transient AuthProvider authProvider = new AuthProvider(this::requestBasicBearer, this::getAuthorization);
    private final transient ExceptionHandler exceptionHandler = new ExceptionHandler(clientAction, this::refreshAuthorization);
    private final transient TokenProvider tokenProvider = new TokenProvider();

    <T extends Response> T query(Request request, Class<T> responseType, String httpMethod) {
        HttpRequest httpRequest = configureBuilder(request, httpMethod).build();
        return execute(httpRequest, responseType);
    }

    private <T extends Response> T execute(HttpRequest request, Class<T> responseType) {
        try {
            HttpResponse httpResponse = requestScheduler.enqueue(request::execute);
            String content = httpResponse.parseAsString();
            if(!content.isEmpty()) {
                exceptionHandler.searchForError(content);
                return gson.fromJson(content, responseType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exceptionHandler.createFallbackResponse(responseType);
    }

    private HttpBuilder configureBuilder(Request request, String httpMethod) {
        return new HttpBuilder()
                .setMcDRequest(request)
                .setHttpMethod(httpMethod)
                .setProxy(null)
                .setAuthorization(authProvider.getAppropriateAuth(request))
                .setUnsuccessfulResponseHandler(new HttpRetryHandler(exceptionHandler))
                .setSensorToken(request.isTokenRequired() ? tokenProvider.getSensorToken(userInfo) : null);
    }

    public BearerAuthorization refreshAuthorization() {
        BearerAuthorization auth = query(new RefreshRequest(authorization.getRefreshToken()),
                LoginResponse.class, HttpMethods.POST).getResponse();
        setAuthorization(auth);
        clientAction.notifyListener(Action.AUTHORIZATION_CHANGED);
        return auth;
    }

    private BasicBearerAuthorization requestBasicBearer() {
        return query(new BasicBearerRequest(), BasicBearerResponse.class, HttpMethods.POST).getResponse();
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
