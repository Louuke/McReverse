package icu.jnet.mcd.utils;

public class UserInfo {
    private String email = "", deviceId = "", userId = "";

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
}
