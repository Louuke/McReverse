package org.jannsen.mcreverse.api.exception;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.jannsen.mcreverse.api.entity.login.BearerAuthorization;
import org.jannsen.mcreverse.api.response.Response;
import org.jannsen.mcreverse.constants.Action;
import org.jannsen.mcreverse.utils.listener.ClientActionNotifier;

import java.io.IOException;

public class HttpResponseHandler implements HttpUnsuccessfulResponseHandler {

    private final Gson gson = new Gson();
    private final ClientActionNotifier clientAction;

    public HttpResponseHandler(ClientActionNotifier clientAction) {
        this.clientAction = clientAction;
    }

    @Override
    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
        if(response.getStatusCode() == 401) {
            if(supportsRetry) {
                String content = response.parseAsString();
                if(getErrorType(content).equals("JWTTokenExpired")) {
                    updateAuthorization(request);
                    return true;
                }
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

    private String getErrorType(String response) {
        Response errorResponse = createErrorResponse(response);
        return errorResponse != null && !errorResponse.getStatus().getErrors().isEmpty()
                ? errorResponse.getStatus().getErrors().get(0).getErrorType()
                : "";
    }

    private Response createErrorResponse(String response) {
        try {
            return gson.fromJson(response, Response.class);
        } catch (JsonSyntaxException ignored) {}
        return null;
    }
}
