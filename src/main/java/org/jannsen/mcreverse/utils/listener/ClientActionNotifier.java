package org.jannsen.mcreverse.utils.listener;

import org.jannsen.mcreverse.constants.Action;

import java.util.ArrayList;
import java.util.List;

public class ClientActionNotifier {

    private final List<ClientActionListener> listeners = new ArrayList<>();

    public void notifyListener(Action action) {
        switch (action) {
            case AUTHORIZATION_CHANGED -> listeners.forEach(ClientActionListener::authChanged);
            case JWT_INVALID -> listeners.forEach(ClientActionListener::jwtIsInvalid);
            case ACCOUNT_DELETED -> listeners.forEach(ClientActionListener::accountDeleted);
        };
    }

    public void addListener(ClientActionListener listener) {
        listeners.add(listener);
    }
}
