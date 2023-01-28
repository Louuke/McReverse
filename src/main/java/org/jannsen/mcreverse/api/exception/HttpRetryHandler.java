package org.jannsen.mcreverse.api.exception;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import org.jannsen.mcreverse.api.entity.auth.BearerAuthorization;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class HttpRetryHandler implements HttpUnsuccessfulResponseHandler {

    private final Supplier<BearerAuthorization> authorizationSupplier;
    private final Consumer<String> searchErrorConsumer;

    public HttpRetryHandler(Supplier<BearerAuthorization> authorizationSupplier, Consumer<String> searchErrorConsumer) {
        this.authorizationSupplier = authorizationSupplier;
        this.searchErrorConsumer = searchErrorConsumer;
    }

    @Override
    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
        if(!supportsRetry) {
            return false;
        }
        searchErrorConsumer.accept(response.parseAsString());
        request.getHeaders().set("authorization", authorizationSupplier.get().getAccessToken(true));
        return true;
    }
}
