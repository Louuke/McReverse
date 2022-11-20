package org.jannsen.mcreverse.api.exception;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import org.jannsen.mcreverse.api.entity.login.BearerAuthorization;
import org.jannsen.mcreverse.constants.Action;
import org.jannsen.mcreverse.utils.listener.ClientActionNotifier;

public class HttpResponseHandler implements HttpUnsuccessfulResponseHandler {

    private final ClientActionNotifier clientAction;

    public HttpResponseHandler(ClientActionNotifier clientAction) {
        this.clientAction = clientAction;
    }

    @Override
    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) {
        if(response.getStatusCode() == 401) {
            if(supportsRetry) {
                updateAuthorization(request);
                return true;
            } else {
                clientAction.notifyListener(Action.JWT_INVALID);
            }
        }
        return false;
    }

    private void updateAuthorization(HttpRequest request) {
        BearerAuthorization authorization = clientAction.notifyListener(Action.JWT_EXPIRED, BearerAuthorization.class);
        request.getHeaders().set("authorization", authorization.getAccessToken(true));
    }
}
