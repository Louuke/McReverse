package icu.jnet.mcd.utils.listener;

import icu.jnet.mcd.api.entity.login.Authorization;

public interface ClientStateListener {

    default Authorization jwtExpired() {
        return null;
    }

    default String tokenRequired() {
        return null;
    }

    default String basicBearerRequired() {
        return null;
    }

    default void jwtIsInvalid() {}

    default void authChanged() {}

    default void accountDeleted() {}
}
