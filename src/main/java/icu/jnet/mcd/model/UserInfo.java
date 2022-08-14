package icu.jnet.mcd.model;

import java.util.Random;

public class UserInfo {

    private static final Random rand = new Random();
    private String email = "", deviceId = "", userId = "";
    private final String uuid = (rand.nextInt(90000) + 10000) + "c4d-e5df-4cbe-92e9-702ca00ddc4c";

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

    public String getUuid() {
        return uuid;
    }
}
