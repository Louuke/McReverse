package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.steam.OfferImage;
import org.jannsen.mcreverse.api.response.status.Status;

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
        return response != null && !response.getData().isEmpty();
    }
}
