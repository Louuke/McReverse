package icu.jnet.mcd.api.exception;

import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.http.HttpRequest;

import java.io.IOException;

public class IOResponseHandler implements HttpIOExceptionHandler {

    @Override
    public boolean handleIOException(HttpRequest request, boolean supportsRetry) throws IOException {
        return true;
    }
}
