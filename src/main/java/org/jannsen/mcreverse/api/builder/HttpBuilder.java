package org.jannsen.mcreverse.api.builder;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.jannsen.mcreverse.api.entity.login.Authorization;
import org.jannsen.mcreverse.api.entity.login.SensorToken;
import org.jannsen.mcreverse.api.exception.HttpResponseHandler;
import org.jannsen.mcreverse.api.exception.IOResponseHandler;
import org.jannsen.mcreverse.api.request.Request;
import org.jannsen.mcreverse.constants.Action;
import org.jannsen.mcreverse.utils.listener.ClientActionModel;

import java.io.IOException;
import java.net.Proxy;
import java.util.*;

public class HttpBuilder {

    private Request mcdRequest;
    private String method = HttpMethods.GET;
    private SensorToken token;
    private Proxy proxy;
    private ClientActionModel actionModel;
    private Authorization authorization;

    public HttpBuilder setMcDRequest(Request request) {
        this.mcdRequest = request;
        return this;
    }

    public HttpBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public HttpBuilder setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public HttpBuilder setActionModel(ClientActionModel actionModel) {
        this.actionModel = actionModel;
        return this;
    }

    public HttpBuilder setAuthorization(Authorization authorization) {
        this.authorization = authorization;
        return this;
    }

    public HttpBuilder setSensorToken(SensorToken token) {
        this.token = token;
        return this;
    }

    public HttpRequest build() {
        HttpRequest request = createRequest(mcdRequest, method, proxy);
        request.setHeaders(createHeaders());
        request.setReadTimeout(mcdRequest.getReadTimeout());
        request.setNumberOfRetries(3);
        request.setSuppressUserAgentSuffix(true);
        request.setThrowExceptionOnExecuteError(false);
        request.setUnsuccessfulResponseHandler(new HttpResponseHandler(actionModel));
        request.setIOExceptionHandler(new IOResponseHandler());
        return request;
    }

    private String getJWTToken(Request request) {
        return switch (request.getAuthType()) {
            case Basic -> "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";
            case BasicBearer -> "Bearer " + actionModel.notifyListener(Action.BASIC_BEARER_REQUIRED, String.class);
            case Bearer -> "Bearer " + authorization.getAccessToken();
        };
    }

    private HttpRequest createRequest(Request request, String method, Proxy proxy) {
        try {
            HttpRequestFactory factory = new NetHttpTransport.Builder().setProxy(proxy).build().createRequestFactory();
            GenericUrl url = new GenericUrl(request.getUrl());
            return factory.buildRequest(method, url, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        headers.set("accept-charset", "UTF-8");
        headers.set("user-agent", "MCDSDK/25.0.15 (Android; 30; de-DE) GMA/7.9");
        headers.set("content-type", mcdRequest.getContent() != null ? mcdRequest.getContent().getType() : "application/json; charset=UTF-8");
        headers.set("accept-language", "de-DE");
        headers.set("mcd-sourceapp", "GMA");
        headers.set("mcd-uuid", UUID.randomUUID());
        headers.set("mcd-marketid", "DE");
        headers.set("accept-encoding", "gzip");
        headers.set("authorization", getJWTToken(mcdRequest));
        if(token != null) headers.set("x-acf-sensor-data", token.getToken());
        return headers;
    }
}
