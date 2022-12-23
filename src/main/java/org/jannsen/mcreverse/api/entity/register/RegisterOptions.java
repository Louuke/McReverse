package org.jannsen.mcreverse.api.entity.register;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class RegisterOptions {

    private final transient Random rand = new Random();
    private final String zipCode, deviceId;
    private String firstName = "X", lastName = "Y";

    public RegisterOptions(@Nullable String zipCode, @Nullable String deviceId) {
        this.zipCode = zipCode != null ? zipCode : createZipCode();
        this.deviceId = deviceId != null ? deviceId : createDeviceId();
    }

    public RegisterOptions() {
        this.zipCode = createZipCode();
        this.deviceId = createDeviceId();
    }

    public RegisterOptions setFirstName(@Nonnull String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterOptions setLastName(@Nonnull String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getZipCode() {
        return zipCode;
    }

    private String createZipCode() {
        return String.format("%05d", 1001 + rand.nextInt(98998));
    }

    private String createDeviceId() {
        return rand.ints(48, 123).filter(i -> !(i >= 58 && i <= 96)).limit(16)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
