package org.jannsen.mcreverse.api.entity.register;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.jannsen.mcreverse.utils.Utils.loadListOfNames;

public class RegisterOptions {

    private final transient List<String> names = loadListOfNames();
    private final transient Random rand = new Random();
    private String zipCode = createZipCode(), deviceId = createDeviceId(), firstName = getRandomName(),
            lastName = getRandomLastname();

    public RegisterOptions(@Nullable String zipCode, @Nullable String deviceId, @Nullable String firstname,
                           @Nullable String lastname) {
        if(zipCode != null) this.zipCode = zipCode;
        if(deviceId != null) this.deviceId = deviceId;
        if(firstname != null) this.firstName = firstname;
        if(lastname != null) this.lastName = lastname;
    }

    public RegisterOptions() {}

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

    public RegisterOptions setDeviceId(@Nonnull String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public RegisterOptions setZipCode(@Nonnull String zipCode) {
        this.zipCode = zipCode;
        return this;
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

    private String getRandomName() {
        return names.get(rand.nextInt(names.size()));
    }

    private String getRandomLastname() {
        return rand.nextInt(2) == 0 ? getRandomName() : String.valueOf(getRandomName().charAt(0));
    }
}
