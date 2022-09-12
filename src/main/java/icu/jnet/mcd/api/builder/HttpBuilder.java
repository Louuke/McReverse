package icu.jnet.mcd.api.builder;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import icu.jnet.mcd.api.exception.HttpResponseHandler;
import icu.jnet.mcd.api.exception.IOResponseHandler;
import icu.jnet.mcd.api.request.Request;
import icu.jnet.mcd.utils.listener.ClientActionModel;

import java.io.IOException;
import java.util.UUID;

import static com.google.api.client.http.HttpMethods.*;

public class HttpBuilder {

    private static final HttpRequestFactory factory = new NetHttpTransport().createRequestFactory();

    private final HttpHeaders headers = new HttpHeaders();
    private final HttpRequest httpRequest;

    public HttpBuilder(Request request, String method, ClientActionModel actionModel) {
        httpRequest = createRequest(request, method);
        httpRequest.setHeaders(headers);
        httpRequest.setSuppressUserAgentSuffix(false);
        httpRequest.setNumberOfRetries(3);
        httpRequest.setThrowExceptionOnExecuteError(false);
        httpRequest.setUnsuccessfulResponseHandler(new HttpResponseHandler(actionModel));
        httpRequest.setIOExceptionHandler(new IOResponseHandler());
        setGenericHeaders(request);
    }

    public HttpBuilder setReadTimeout(int timout) {
        httpRequest.setReadTimeout(timout);
        return this;
    }

    public HttpBuilder setAuthorization(String authorization) {
        headers.set("authorization", authorization);
        return this;
    }

    public HttpBuilder setSensorToken(String token) {
        if(token != null) headers.set("x-acf-sensor-data", token);
        return this;
    }

    public HttpRequest build() {
        return httpRequest;
    }

    private HttpRequest createRequest(Request request, String method) {
        try {
            GenericUrl url = new GenericUrl(request.getUrl());
            return switch (method) {
                case GET -> factory.buildGetRequest(url);
                case POST -> factory.buildPostRequest(url, request.getContent());
                case PUT -> factory.buildPutRequest(url, request.getContent());
                case DELETE -> factory.buildDeleteRequest(url);
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}
