package org.jannsen.mcreverse.api.exception;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.jannsen.mcreverse.api.response.Response;
import org.jannsen.mcreverse.api.response.status.Status;
import org.jannsen.mcreverse.constants.Action;
import org.jannsen.mcreverse.utils.listener.ClientActionNotifier;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public class ExceptionHandler {

    private final Gson gson = new Gson();
    private final ClientActionNotifier clientAction;

    public ExceptionHandler(@Nonnull ClientActionNotifier clientAction) {
        this.clientAction = clientAction;
    }

    public void searchForError(@Nonnull String strResponse) {
        Response response = createDummyResponse(Response.class, strResponse);
        switch (searchErrorCode(response)) {
            case 40003 -> clientAction.notifyListener(Action.JWT_INVALID); // Authorization is invalid and can't be recovered
            case 11310, 41471 -> clientAction.notifyListener(Action.ACCOUNT_DELETED);
        };
    }

    private <T extends Response> int searchErrorCode(@Nonnull T response) {
        return response.getStatus().getErrors().stream().map(Status.Error::getErrorCode).findAny().orElse(0);
    }

    public <T extends Response> T createDummyResponse(Class<T> clazz) {
        return createDummyResponse(clazz, null);
    }

    public <T extends Response> T createDummyResponse(Class<T> clazz, String strResponse) {
        try {
            Response response = gson.fromJson(strResponse, Response.class);
            return clazz.getConstructor(Status.class).newInstance(response != null ? response.getStatus() : new Status());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            System.err.println("JSON error: " + e.getMessage());
            return createDummyResponse(clazz);
        }
    }
}
