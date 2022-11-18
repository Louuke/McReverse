package org.jannsen.mcreverse.utils.listener;

import org.jannsen.mcreverse.api.entity.login.Authorization;
import org.jannsen.mcreverse.api.entity.login.SensorToken;

public interface ClientStateListener {

    default Authorization jwtExpired() {
        return null;
    }

    default SensorToken tokenRequired() {
        return null;
    }

    default String basicBearerRequired() {
        return null;
    }

    default void jwtIsInvalid() {}

    default void authChanged() {}

    default void accountDeleted() {}

    default void requestTimedOut() {}
}
