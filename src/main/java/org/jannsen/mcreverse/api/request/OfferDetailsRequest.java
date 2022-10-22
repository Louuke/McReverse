package org.jannsen.mcreverse.api.request;

public class OfferDetailsRequest extends Request {

    private final int propositionId;

    public OfferDetailsRequest(int propositionId) {
        this.propositionId = propositionId;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/offers/details/" + propositionId;
    }
}
