package org.jannsen.mcreverse.utils.listener;

import org.jannsen.mcreverse.api.entity.auth.BearerAuthorization;
import org.jannsen.mcreverse.constants.Action;

import java.util.ArrayList;
import java.util.List;

public class ClientActionNotifier {

    private final List<ClientActionListener> listeners = new ArrayList<>();

    public void notifyListener(Action action) {
        switch (action) {
            case JWT_INVALID -> listeners.forEach(ClientActionListener::jwtIsInvalid);
            case ACCOUNT_DELETED -> listeners.forEach(ClientActionListener::accountDeleted);
            case AUTHORIZATION_REFRESH_REQUIRED -> listeners.forEach(ClientActionListener::authRefreshRequired);
            default -> {}
        }
    }

    public void notifyAuthorizationChanged(BearerAuthorization authorization) {
        listeners.forEach(action -> action.authChanged(authorization));
    }

    public void addListener(ClientActionListener listener) {
        listeners.add(listener);
    }
}
