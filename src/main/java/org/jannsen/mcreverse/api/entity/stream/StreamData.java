package org.jannsen.mcreverse.api.entity.stream;

import org.springframework.data.annotation.Id;

import java.time.Instant;

public class StreamData {

    @Id
    private String name;
    private String data, type;
    private long createdTime = Instant.now().getEpochSecond();

    public StreamData() {}

    public StreamData(String name, String data, String type) {
        this.name = name;
        this.data = data;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getDataType() {
        return type;
    }

    public long getCreatedTime() {
        return createdTime;
    }
}
