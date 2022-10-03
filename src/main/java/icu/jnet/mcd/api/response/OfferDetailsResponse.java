package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.entity.details.OfferDetails;
import icu.jnet.mcd.api.response.status.Status;

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
