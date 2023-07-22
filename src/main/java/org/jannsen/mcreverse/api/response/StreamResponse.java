package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.stream.StreamData;
import org.jannsen.mcreverse.api.response.status.Status;

import java.util.Base64;

import static org.jannsen.mcreverse.utils.Utils.detectMimeType;

public class StreamResponse extends Response {

    private StreamData response;

    public StreamResponse(Status status) {
        super(status);
    }

    public StreamResponse(String url, String data, long length) {
        super(new Status());
        String mimeType = detectMimeType(data);
        String[] dirs = url.split("/");
        this.response = new StreamData(dirs[dirs.length -1], data, mimeType, length);
    }

    @Override
    public StreamData getResponse() {
        return response;
    }

    @Override
    public boolean success() {
        return response != null && Base64.getDecoder().decode(response.getData()).length == response.getLength();
    }
}
