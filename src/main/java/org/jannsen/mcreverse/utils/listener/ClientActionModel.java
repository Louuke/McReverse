package org.jannsen.mcreverse.utils.listener;

import org.jannsen.mcreverse.constants.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientActionModel {

    private final List<ClientStateListener> stateListener = new ArrayList<>();

    public void notifyListener(Action action) {
        switch (action) {
            case AUTHORIZATION_CHANGED -> stateListener.forEach(ClientStateListener::authChanged);
            case JWT_INVALID -> stateListener.forEach(ClientStateListener::jwtIsInvalid);
            case ACCOUNT_DELETED -> stateListener.forEach(ClientStateListener::accountDeleted);
            case REQUEST_TIMED_OUT -> stateListener.forEach(ClientStateListener::requestTimedOut);
        };
    }

    public <T> T notifyListener(Action action, Class<T> returnType) {
        return returnType.cast(switch (action) {
            case JWT_EXPIRED -> stateListener.stream()
                    .map(ClientStateListener::jwtExpired)
                    .filter(Objects::nonNull).findAny().orElse(null);
            case TOKEN_REQUIRED -> stateListener.stream()
                    .map(ClientStateListener::tokenRequired)
                    .filter(Objects::nonNull).findAny().orElse(null);
            case BASIC_BEARER_REQUIRED -> stateListener.stream()
                    .map(ClientStateListener::basicBearerRequired)
                    .filter(Objects::nonNull).findAny().orElse(null);
            default -> null;
        });
    }

    public void addStateListener(ClientStateListener listener) {
        stateListener.add(listener);
    }
}
