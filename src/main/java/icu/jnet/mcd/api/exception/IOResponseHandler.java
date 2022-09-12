package icu.jnet.mcd.api.exception;

import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.http.HttpRequest;

import java.io.IOException;

public class IOResponseHandler implements HttpIOExceptionHandler {

    private final String email;

    public IOResponseHandler(String email) {
        this.email = email;
    }

    @Override
    public boolean handleIOException(HttpRequest request, boolean supportsRetry) throws IOException {
        System.out.println(System.currentTimeMillis() + ": " + email + ": IOException: " + request.getUrl());
        return true;
    }
}
