package org.jannsen.mcreverse.api.entity.stream;

import org.springframework.data.annotation.Id;

public class StreamData {

    @Id
    private String name;
    private String data, type;
    private long length;

    public StreamData() {}

    public StreamData(String name, String data, String type, long length) {
        this.name = name;
        this.data = data;
        this.type = type;
        this.length = length;
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

    public long getLength() {
        return length;
    }
}
