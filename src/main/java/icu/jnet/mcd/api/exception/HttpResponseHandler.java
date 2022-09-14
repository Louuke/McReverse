package icu.jnet.mcd.api.exception;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.utils.listener.Action;
import icu.jnet.mcd.utils.listener.ClientActionModel;

import java.io.IOException;

public class HttpResponseHandler implements HttpUnsuccessfulResponseHandler {

    private final Gson gson = new Gson();
    private final ClientActionModel actionModel;

    public HttpResponseHandler(ClientActionModel actionModel) {
        this.actionModel = actionModel;
    }

    @Override
    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
        String content = response.parseAsString();
        if(response.getStatusCode() == 401 && getErrorType(content).equals("JWTTokenExpired")) {
            updateAuthorization(request);
            return true;
        }
        return false;
    }

    private void updateAuthorization(HttpRequest request) {
        Authorization authorization = actionModel.notifyListener(Action.JWT_EXPIRED, Authorization.class);
        request.getHeaders().set("authorization", "Bearer " + authorization.getAccessToken());
    }

    private String getErrorType(String response) {
        Response errorResponse = createErrorResponse(response);
        return errorResponse != null
                ? !errorResponse.getStatus().getErrors().isEmpty()
                ? errorResponse.getStatus().getErrors().get(0).getErrorType()
                : "" : "";
    }

    private Response createErrorResponse(String response) {
        try {
            return gson.fromJson(response, Response.class);
        } catch (JsonSyntaxException ignored) {}
        return null;
    }
}
