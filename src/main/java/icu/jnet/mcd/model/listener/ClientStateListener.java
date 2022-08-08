package icu.jnet.mcd.model.listener;

public interface ClientStateListener {

    default void loginExpired() {}

    default <T> void changed(T source) {}

    default String tokenRequired() {
        return null;
    }
}
