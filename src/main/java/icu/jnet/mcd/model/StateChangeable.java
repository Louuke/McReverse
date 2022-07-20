package icu.jnet.mcd.model;

import icu.jnet.mcd.model.listener.StateChangeListener;

import java.util.ArrayList;
import java.util.List;

public abstract class StateChangeable {

    private transient List<StateChangeListener> changeListeners;

    protected <T> void notifyListeners(T source) {
        for(StateChangeListener listener : getListeners()) {
            if(listener != null) {
                listener.changed(source);
            }
        }
    }

    public boolean addChangeListener(StateChangeListener listener) {
        return getListeners().add(listener);
    }

    public boolean removeChangeListener(StateChangeListener listener) {
        return getListeners().remove(listener);
    }

    private List<StateChangeListener> getListeners() {
        changeListeners = changeListeners != null ? changeListeners : new ArrayList<>();
        return changeListeners;
    }
}
