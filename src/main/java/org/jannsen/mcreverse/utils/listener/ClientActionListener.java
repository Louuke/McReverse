package org.jannsen.mcreverse.utils.listener;

public interface ClientActionListener {

    default void jwtIsInvalid() {}

    default void authChanged() {}

    default void accountDeleted() {}
}
