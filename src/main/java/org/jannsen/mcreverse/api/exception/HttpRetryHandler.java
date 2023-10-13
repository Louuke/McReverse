package org.jannsen.mcreverse.api.exception;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import org.jannsen.mcreverse.api.entity.auth.Authorization;
import org.jannsen.mcreverse.api.entity.auth.BearerAuthorization;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class HttpRetryHandler implements HttpUnsuccessfulResponseHandler {

    private final Supplier<Authorization> authorization;
    private final Runnable refreshAuthorization;

    public HttpRetryHandler(Supplier<Authorization> authorization, Runnable refreshAuthorization) {
        this.authorization = authorization;
        this.refreshAuthorization = refreshAuthorization;
    }

    @Override
    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
        if(!supportsRetry) {
            return false;
        }
        refreshAuthorization.run();
        System.out.println("Retry");
        request.getHeaders().set("authorization", authorization.get().getAccessToken(true));
        return true;
    }
}
