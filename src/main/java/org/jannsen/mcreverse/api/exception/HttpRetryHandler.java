package org.jannsen.mcreverse.api.exception;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;

import java.io.IOException;

public class HttpRetryHandler implements HttpUnsuccessfulResponseHandler {

    private final ExceptionHandler exceptionHandler;

    public HttpRetryHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
        if(!supportsRetry) {
            return false;
        }
        exceptionHandler.refreshAuthorization(request, response.parseAsString());
        return true;
    }
}
