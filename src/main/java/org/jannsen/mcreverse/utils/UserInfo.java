package org.jannsen.mcreverse.utils;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

@Embeddable
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
