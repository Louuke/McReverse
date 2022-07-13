package icu.jnet.mcd.model;

import icu.jnet.mcd.model.listener.StateChangeListener;

import java.util.ArrayList;
import java.util.List;

public abstract class StateChangeable {

    private final List<StateChangeListener> changeListeners = new ArrayList<>();

    protected <T> void notifyListeners(T source) {
        for(StateChangeListener listener : changeListeners) {
            listener.changed(source);
        }
    }

    public boolean addChangeListener(StateChangeListener listener) {
        return changeListeners.add(listener);
    }

    public boolean removeChangeListener(StateChangeListener listener) {
        return changeListeners.remove(listener);
    }
}
