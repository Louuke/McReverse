package org.jannsen.mcreverse.api.request.builder;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.jannsen.mcreverse.api.entity.auth.Authorization;
import org.jannsen.mcreverse.api.entity.akamai.SensorToken;
import org.jannsen.mcreverse.api.exception.IOResponseHandler;
import org.jannsen.mcreverse.api.request.Request;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.Proxy;
import java.util.*;

import static com.google.api.client.http.HttpMethods.*;

public class HttpBuilder {

    private Request mcdRequest;
    private String httpMethod = GET;
    private SensorToken token;
    private Proxy proxy;
    private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
    private Authorization authorization;

    public HttpBuilder setMcDRequest(@Nonnull Request request) {
        this.mcdRequest = request;
        return this;
    }

    public HttpBuilder setHttpMethod(@Nonnull String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public HttpBuilder setProxy(@Nullable Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public HttpBuilder setAuthorization(@Nonnull Authorization authorization) {
        this.authorization = authorization;
        return this;
    }

    public HttpBuilder setSensorToken(@Nullable SensorToken token) {
        this.token = token;
        return this;
    }

    public HttpBuilder setUnsuccessfulResponseHandler(@Nonnull HttpUnsuccessfulResponseHandler handler) {
        this.unsuccessfulResponseHandler = handler;
        return this;
    }

    public HttpRequest build() {
        HttpRequest request = createRequest(mcdRequest, httpMethod, proxy);
        request.setHeaders(createHeaders());
        request.setReadTimeout(mcdRequest.getReadTimeout());
        request.setNumberOfRetries(1);
        request.setSuppressUserAgentSuffix(true);
        request.setThrowExceptionOnExecuteError(false);
        request.setUnsuccessfulResponseHandler(unsuccessfulResponseHandler);
        request.setIOExceptionHandler(new IOResponseHandler());
        return request;
    }

    private HttpRequest createRequest(Request request, String method, Proxy proxy) {
        try {
            HttpRequestFactory factory = new NetHttpTransport.Builder().setProxy(proxy).build().createRequestFactory();
            GenericUrl url = new GenericUrl(request.getUrl());
            return switch (method) {
                case POST, PUT -> factory.buildRequest(method, url, request.getContent());
                default -> factory.buildRequest(method, url, null);
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        headers.set("accept-charset", "UTF-8");
        headers.set("user-agent", "MCDSDK/27.0.18 (Android; 30; de-DE) GMA/7.11");
        headers.set("content-type", mcdRequest.getContent() != null ? mcdRequest.getContent().getType() : "application/json; charset=UTF-8");
        headers.set("accept-language", "de-DE");
        headers.set("mcd-sourceapp", "GMA");
        headers.set("mcd-uuid", UUID.randomUUID());
        headers.set("mcd-marketid", "DE");
        headers.set("accept-encoding", "gzip");
        headers.set("authorization", authorization.getAccessToken(true));
        if(token != null) headers.set("x-acf-sensor-data", token.getToken());
        return headers;
    }
}
