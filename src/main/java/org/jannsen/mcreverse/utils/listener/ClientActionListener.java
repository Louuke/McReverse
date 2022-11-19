package org.jannsen.mcreverse.utils.listener;

import org.jannsen.mcreverse.api.entity.login.BearerAuthorization;
import org.jannsen.mcreverse.api.entity.login.SensorToken;

public interface ClientActionListener {

    default BearerAuthorization jwtExpired() {
        return null;
    }

    default SensorToken tokenRequired() {
        return null;
    }

    default void jwtIsInvalid() {}

    default void authChanged() {}

    default void accountDeleted() {}

    default void requestTimedOut() {}
}
