package icu.jnet.mcd.utils;

import java.util.Objects;
import java.util.Random;

public class UserInfo {

    private final Random rand = new Random();
    private String email = "", deviceId = rdmDeviceId(), userId = "";

    public String getEmail() {
        return email;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public UserInfo setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public UserInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    private String rdmDeviceId() {
        return rand.ints(48, 123).filter(i -> !(i >= 58 && i <= 96)).limit(16)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserInfo user)) {
            return false;
        }
        return user.getEmail().equals(email);
    }
}
