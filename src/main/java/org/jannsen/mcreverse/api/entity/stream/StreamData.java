package org.jannsen.mcreverse.api.entity.stream;

import org.springframework.data.annotation.Id;

import java.time.Instant;

public class StreamData {

    @Id
    private String name;
    private String data;
    private final long createdTime;

    public StreamData() {
        this.createdTime = Instant.now().getEpochSecond();
    }

    public StreamData(String name, String data) {
        this();
        this.name = name;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public long getCreatedTime() {
        return createdTime;
    }
}
