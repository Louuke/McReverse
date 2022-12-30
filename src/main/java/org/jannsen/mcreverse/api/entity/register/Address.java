package org.jannsen.mcreverse.api.entity.register;

public class Address {

    private final String zipCode, country = "DE";

    public Address(String zipCode) {
        this.zipCode = zipCode;
    }
}
