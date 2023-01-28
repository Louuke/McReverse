package org.jannsen.mcreverse.utils.listener;

import org.jannsen.mcreverse.api.entity.auth.BearerAuthorization;

public interface ClientActionListener {

    default void jwtIsInvalid() {}

    default void authChanged(BearerAuthorization authorization) {}

    default void authRefreshRequired() {}

    default void accountDeleted() {}
}
