package org.jannsen.mcreverse.api.exception;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.jannsen.mcreverse.api.response.Response;
import org.jannsen.mcreverse.api.response.status.Status;
import org.jannsen.mcreverse.constants.Action;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class ExceptionHandler {

    private final Gson gson = new Gson();
    private final Consumer<Action> actionConsumer;

    public ExceptionHandler(@Nonnull Consumer<Action> actionConsumer) {
        this.actionConsumer = actionConsumer;
    }

    public void searchResponseError(@Nonnull String responseContent) {
        Response response = createFallbackResponse(Response.class, responseContent);
        Action action = switch (searchErrorCode(response)) {
            case 40003 -> Action.JWT_INVALID; // Authorization is invalid and can't be recovered
            case 11310, 41471 -> Action.ACCOUNT_DELETED;
            case 40006 -> Action.AUTHORIZATION_REFRESH_REQUIRED; // JWTTokenExpired
            default -> Action.UNKNOWN_ERROR;
        };
        actionConsumer.accept(action);
    }

    private <T extends Response> int searchErrorCode(@Nonnull T response) {
        return response.getStatus().getErrors().stream().map(Status.Error::getErrorCode).findAny().orElse(0);
    }

    public byte[] createFallbackResponse() {
        return new byte[0];
    }

    public <T extends Response> T createFallbackResponse(Class<T> clazz) {
        return createFallbackResponse(clazz, null);
    }

    private <T extends Response> T createFallbackResponse(Class<T> clazz, String responseContent) {
        if(!validJsonResponse(responseContent)) {
            return createFallbackResponse(clazz);
        }
        try {
            Response response = gson.fromJson(responseContent, Response.class);
            return clazz.getConstructor(Status.class).newInstance(response != null ? response.getStatus() : new Status());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validJsonResponse(String content) {
        try {
            gson.fromJson(content, Response.class);
        } catch (JsonSyntaxException e) {
            return false;
        }
        return true;
    }
}
