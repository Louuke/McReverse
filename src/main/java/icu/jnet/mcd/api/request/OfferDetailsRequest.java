package icu.jnet.mcd.api.request;

public class OfferDetailsRequest implements Request {

    private final String propositionId;

    public OfferDetailsRequest(String propositionId) {
        this.propositionId = propositionId;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/offers/details/" + propositionId;
    }
}
