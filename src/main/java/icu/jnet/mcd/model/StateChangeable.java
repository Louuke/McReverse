package icu.jnet.mcd.model;

import icu.jnet.mcd.model.listener.ClientStateListener;

import java.util.ArrayList;
import java.util.List;

public abstract class StateChangeable {

    private final transient List<ClientStateListener> stateListener = new ArrayList<>();

    protected <T extends StateChangeable> void notifyListeners(T source) {
        stateListener.forEach(listener -> listener.changed(source));
    }

    public void addStateListener(ClientStateListener listener) {
        stateListener.add(listener);
    }
}
