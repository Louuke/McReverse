package org.jannsen.mcreverse.api.entity.profile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Base {

    private String firstName, lastName;
    private String username;

    public Base setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Base setLastName(@Nullable String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }
}
