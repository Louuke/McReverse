package icu.jnet.mcd.api.builder;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import icu.jnet.mcd.api.McClientSettings;
import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.api.exception.HttpResponseHandler;
import icu.jnet.mcd.api.exception.IOResponseHandler;
import icu.jnet.mcd.api.request.Request;
import icu.jnet.mcd.constants.Action;
import icu.jnet.mcd.utils.UserInfo;
import icu.jnet.mcd.utils.listener.ClientActionModel;

import java.io.IOException;
import java.net.Proxy;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.api.client.http.HttpMethods.*;

public class HttpBuilder {

    private static final Map<UserInfo, Map<String, Object>> cachedFactories = new ConcurrentHashMap<>();
    private static final Random rand = new Random();
    private final HttpHeaders headers = new HttpHeaders();
    private final Request mcdRequest;
    private final HttpRequest httpRequest;
    private final ClientActionModel actionModel;
    private Authorization authorization;

    public HttpBuilder(UserInfo user, Request mcdRequest, String method, ClientActionModel actionModel) {
        httpRequest = createRequest(user, mcdRequest, method);
        httpRequest.setHeaders(headers);
        httpRequest.setReadTimeout(mcdRequest.getReadTimeout());
        httpRequest.setSuppressUserAgentSuffix(false);
        httpRequest.setNumberOfRetries(3);
        httpRequest.setThrowExceptionOnExecuteError(false);
        httpRequest.setUnsuccessfulResponseHandler(new HttpResponseHandler(actionModel));
        httpRequest.setIOExceptionHandler(new IOResponseHandler());
        this.mcdRequest = mcdRequest;
        this.actionModel = actionModel;
    }

    public HttpBuilder setAuthorization(Authorization authorization) {
        this.authorization = authorization;
        return this;
    }

    public HttpBuilder setSensorToken(String token) {
        if(token != null) headers.set("x-acf-sensor-data", token);
        return this;
    }

    public HttpRequest build() {
        setGenericHeaders(mcdRequest);
        headers.set("authorization", getJWTToken(mcdRequest));
        return httpRequest;
    }

    private HttpRequest createRequest(UserInfo user, Request request, String method) {
        try {
            HttpRequestFactory factory = getFactory(user);
            GenericUrl url = new GenericUrl(request.getUrl());
            return switch (method) {
                case GET -> factory.buildGetRequest(url);
                case POST -> factory.buildPostRequest(url, request.getContent());
                case PUT -> factory.buildPutRequest(url, request.getContent());
                case DELETE -> factory.buildDeleteRequest(url);
                default -> factory.buildRequest(method, url, null);
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequestFactory getFactory(UserInfo user) {
        HttpRequestFactory factory;
        long now = Instant.now().getEpochSecond();
        if(!cachedFactories.containsKey(user) || ((Long) cachedFactories.get(user).get("time") < now - 300)) {
            Proxy proxy = getRandomProxy();
            factory = new NetHttpTransport.Builder().setProxy(proxy).build().createRequestFactory();
            Map<String, Object> info = new ConcurrentHashMap<>();
            info.put("time", now);
            info.put("factory", factory);
            cachedFactories.put(user, info);
        } else {
            factory = (HttpRequestFactory) cachedFactories.get(user).get("factory");
        }
        return factory;
    }

    private Proxy getRandomProxy() {
        List<Proxy> list = McClientSettings.getProxies();
        return list.get(rand.nextInt(list.size()));
    }

    private void setGenericHeaders(Request request) {
        headers.set("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        headers.set("accept-charset", "UTF-8");
        headers.set("content-type", request.getContent() != null ? request.getContent().getType() : "application/json;");
        headers.set("accept-language", "de-DE");
        headers.set("user-agent", "MCDSDK/22.0.20 (Android; 30; de-DE) GMA/7.8");
        headers.set("mcd-sourceapp", "GMA");
        headers.set("mcd-uuid", UUID.randomUUID());
        headers.set("mcd-marketid", "DE");
    }

    private String getJWTToken(Request request) {
        return switch (request.getAuthType()) {
            case Basic -> "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";
            case BasicBearer -> "Bearer " + actionModel.notifyListener(Action.BASIC_BEARER_REQUIRED, String.class);
            case Bearer -> "Bearer " + authorization.getAccessToken();
        };
    }
}
