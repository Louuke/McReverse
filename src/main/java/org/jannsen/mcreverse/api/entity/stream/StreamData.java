package org.jannsen.mcreverse.api.entity.stream;

import org.springframework.data.annotation.Id;

import java.time.Instant;

public class StreamData {

    @Id
    private final String name;
    private final String data;
    private final long createdTime = Instant.now().getEpochSecond();

    public StreamData(String name, String data) {
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
