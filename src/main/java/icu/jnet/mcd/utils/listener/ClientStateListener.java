package icu.jnet.mcd.utils.listener;

import icu.jnet.mcd.api.entity.login.Authorization;

public interface ClientStateListener {

    default void loginExpired(String message) {}

    default void authChanged(Authorization authorization) {}

    default void newSensorToken(String token) {}

    default String tokenRequired() {
        return null;
    }
}
