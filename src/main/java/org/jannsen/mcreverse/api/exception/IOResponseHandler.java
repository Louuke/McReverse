package org.jannsen.mcreverse.api.exception;

import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.http.HttpRequest;

public class IOResponseHandler implements HttpIOExceptionHandler {

    @Override
    public boolean handleIOException(HttpRequest request, boolean supportsRetry) {
        return true;
    }
}
