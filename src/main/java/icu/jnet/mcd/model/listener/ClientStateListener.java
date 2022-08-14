package icu.jnet.mcd.model.listener;

import icu.jnet.mcd.model.StateChangeable;

public interface ClientStateListener {

    default void loginExpired() {}

    default <T extends StateChangeable> void changed(T source) {}

    default String tokenRequired() {
        return null;
    }
}
