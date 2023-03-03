package org.jannsen.mcreverse.api.response;

import org.apache.http.entity.ContentType;
import org.jannsen.mcreverse.api.entity.stream.StreamData;
import org.jannsen.mcreverse.api.response.status.Status;

import static org.jannsen.mcreverse.utils.Utils.detectMimeType;

public class StreamResponse extends Response {

    private StreamData response;

    public StreamResponse(Status status) {
        super(status);
    }

    public StreamResponse(String url, String data) {
        super(new Status());
        String[] dirs = url.split("/");
        String mimeType = detectMimeType(data);
        this.response = new StreamData(dirs[dirs.length -1], data, mimeType);
    }

    @Override
    public StreamData getResponse() {
        return response;
    }

    @Override
    public boolean success() {
        return response != null && response.getDataType().equals(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
    }
}
