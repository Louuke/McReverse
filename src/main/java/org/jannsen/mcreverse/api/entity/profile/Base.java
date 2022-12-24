package org.jannsen.mcreverse.api.entity.profile;

import javax.annotation.Nonnull;

public class Base {

    private String firstName = "M", lastName = "M";
    private String username;

    public Base setFirstName(@Nonnull String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Base setLastName(@Nonnull String lastName) {
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
