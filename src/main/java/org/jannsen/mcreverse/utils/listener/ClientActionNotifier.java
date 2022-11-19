package org.jannsen.mcreverse.utils.listener;

import org.jannsen.mcreverse.constants.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientActionNotifier {

    private final List<ClientActionListener> listeners = new ArrayList<>();

    public ClientActionNotifier() {}

    public ClientActionNotifier(ClientActionListener listener) {
        addListener(listener);
    }

    public void notifyListener(Action action) {
        switch (action) {
            case AUTHORIZATION_CHANGED -> listeners.forEach(ClientActionListener::authChanged);
            case JWT_INVALID -> listeners.forEach(ClientActionListener::jwtIsInvalid);
            case ACCOUNT_DELETED -> listeners.forEach(ClientActionListener::accountDeleted);
            case REQUEST_TIMED_OUT -> listeners.forEach(ClientActionListener::requestTimedOut);
        };
    }

    public <T> T notifyListener(Action action, Class<T> returnType) {
        return returnType.cast(switch (action) {
            case JWT_EXPIRED -> listeners.stream()
                    .map(ClientActionListener::jwtExpired)
                    .filter(Objects::nonNull).findAny().orElse(null);
            case TOKEN_REQUIRED -> listeners.stream()
                    .map(ClientActionListener::tokenRequired)
                    .filter(Objects::nonNull).findAny().orElse(null);
            default -> null;
        });
    }

    public void addListener(ClientActionListener listener) {
        listeners.add(listener);
    }
}
