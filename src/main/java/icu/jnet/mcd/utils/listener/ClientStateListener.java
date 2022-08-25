package icu.jnet.mcd.utils.listener;

public interface ClientStateListener {

    default void loginExpired(String message) {}

    default void authChanged() {}

    default void newSensorToken(String token) {}

    default String tokenRequired() {
        return null;
    }
}
