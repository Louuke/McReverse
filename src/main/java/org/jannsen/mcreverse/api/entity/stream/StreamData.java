package org.jannsen.mcreverse.api.entity.stream;

import org.springframework.data.annotation.Id;

import java.time.Instant;

public class StreamData {

    @Id
    private String name;
    private String data;
    private long createdTime = Instant.now().getEpochSecond();

    public StreamData() {}

    public StreamData(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public StreamData(String name, String data, long createdTime) {
        this(name, data);
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public long getCreatedTime() {
        return createdTime;
    }
}