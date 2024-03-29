package org.jannsen.mcreverse.api.entity.akamai;

import org.springframework.data.annotation.Id;

import java.time.Instant;

public class SensorToken {

    @Id
    private String token;
    private long createdTime = Instant.now().getEpochSecond();

    public SensorToken() {}

    public SensorToken(String token) {
        this.token = token;
    }
    public SensorToken(String token, long createdTime) {
        this.token = token;
        this.createdTime = createdTime;
    }

    public String getToken() {
        return token;
    }

    public long getCreatedTime() {
        return createdTime;
    }
}
