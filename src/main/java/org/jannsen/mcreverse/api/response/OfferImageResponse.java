package org.jannsen.mcreverse.api.response;

import org.apache.http.entity.ContentType;
import org.jannsen.mcreverse.api.entity.stream.OfferImage;
import org.jannsen.mcreverse.api.response.status.Status;

import java.util.Arrays;
import java.util.Base64;

public class OfferImageResponse extends Response {

    private OfferImage response;

    public OfferImageResponse(Status status) {
        super(status);
    }

    @Override
    public OfferImage getResponse() {
        return response;
    }

    @Override
    public boolean success() {
        return response != null && Base64.getDecoder().decode(response.getData()).length == response.getLength()
                && Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_WEBP.getMimeType()).contains(response.getDataType());
    }
}
