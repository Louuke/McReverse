package org.jannsen.mcreverse.api.exception;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;

import java.io.IOException;

public class HttpRetryHandler implements HttpUnsuccessfulResponseHandler {

    @Override
    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) {
        return supportsRetry;
    }
}
