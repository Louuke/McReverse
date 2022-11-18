package org.jannsen.mcreverse.api.entity.login;

import java.time.Instant;

public class SensorToken {

    private String token;
    private final long createdTime = Instant.now().getEpochSecond();

    public SensorToken() {}

    public SensorToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public long getCreatedTime() {
        return createdTime;
    }
}
