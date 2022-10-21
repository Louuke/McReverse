package org.jannsen.mcd.api.builder;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.jannsen.mcd.api.entity.login.Authorization;
import org.jannsen.mcd.api.exception.HttpResponseHandler;
import org.jannsen.mcd.api.exception.IOResponseHandler;
import org.jannsen.mcd.api.request.Request;
import org.jannsen.mcd.constants.Action;
import org.jannsen.mcd.utils.listener.ClientActionModel;

import java.io.IOException;
import java.net.Proxy;
import java.util.*;

import static com.google.api.client.http.HttpMethods.*;

public class HttpBuilder {

    private final HttpHeaders headers = new HttpHeaders();
    private final Request mcdRequest;
    private final HttpRequest httpRequest;
    private final ClientActionModel actionModel;
    private Authorization authorization;

    public HttpBuilder(Request mcdRequest, String method, Proxy proxy, ClientActionModel actionModel) {
        httpRequest = createRequest(mcdRequest, method, proxy);
        httpRequest.setHeaders(headers);
        httpRequest.setReadTimeout(mcdRequest.getReadTimeout());
        httpRequest.setNumberOfRetries(3);
        httpRequest.setSuppressUserAgentSuffix(true);
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

    private HttpRequest createRequest(Request request, String method, Proxy proxy) {
        try {
            HttpRequestFactory factory = new NetHttpTransport.Builder().setProxy(proxy).build().createRequestFactory();
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

    private void setGenericHeaders(Request request) {
        headers.set("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        headers.set("accept-charset", "UTF-8");
        headers.set("user-agent", "MCDSDK/22.0.20 (Android; 31; de-DE) GMA/7.8");
        headers.set("content-type", request.getContent() != null ? request.getContent().getType() : "application/json; charset=UTF-8");
        headers.set("accept-language", "de-DE");
        headers.set("mcd-sourceapp", "GMA");
        headers.set("mcd-uuid", UUID.randomUUID());
        headers.set("mcd-marketid", "DE");
        headers.set("accept-encoding", "gzip");
    }

    private String getJWTToken(Request request) {
        return switch (request.getAuthType()) {
            case Basic -> "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";
            case BasicBearer -> "Bearer " + actionModel.notifyListener(Action.BASIC_BEARER_REQUIRED, String.class);
            case Bearer -> "Bearer " + authorization.getAccessToken();
        };
    }
}
