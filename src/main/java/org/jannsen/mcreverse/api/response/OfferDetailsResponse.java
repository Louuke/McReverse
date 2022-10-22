package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.details.OfferDetails;
import org.jannsen.mcreverse.api.response.status.Status;

public class OfferDetailsResponse extends Response {

    private OfferDetails response;

    public OfferDetailsResponse(Status status) {
        super(status);
    }

    public OfferDetails getResponse() {
        return response;
    }

    @Override
    public boolean success() {
        return getStatus().getType().equals("Absolute Success");
    }
}
