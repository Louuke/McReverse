package org.jannsen.mcreverse.utils.listener;

import org.jannsen.mcreverse.api.entity.akamai.SensorToken;

public interface ClientActionListener {

    default SensorToken tokenRequired() {
        return null;
    }

    default void jwtIsInvalid() {}

    default void authChanged() {}

    default void accountDeleted() {}
}
