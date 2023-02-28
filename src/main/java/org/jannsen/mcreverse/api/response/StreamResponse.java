package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.stream.StreamData;
import org.jannsen.mcreverse.api.response.status.Status;

public class StreamResponse extends Response {

    private StreamData response;

    public StreamResponse(Status status) {
        super(status);
    }

    public StreamResponse(String url, String data) {
        super(new Status());
        String[] dirs = url.split("/");
        this.response = new StreamData(dirs[dirs.length -1], data);
    }

    @Override
    public StreamData getResponse() {
        return response;
    }

    @Override
    public boolean success() {
        return response != null && !response.getData().isEmpty();
    }
}
