package icu.jnet.mcd.utils.listener;


import icu.jnet.mcd.api.entity.response.Authorization;

public interface ClientStateListener {

    default void loginExpired(Authorization expiredAuth) {}

    default void authChanged(Authorization newAuth) {}

    default String tokenRequired() {
        return null;
    }
}
