package icu.jnet.mcd.api.request;

public class RedeemRequest implements Request {

    private String propositionId, offerId;

    public RedeemRequest(String propositionId, String offerId) {
        this.propositionId = propositionId;
        this.offerId = offerId;
    }

    @Override
    public String getUrl() {
        String url = "https://eu-prod.api.mcd.com/exp/v1/offers/redemption/" + propositionId;
        return url + (!offerId.equals("0") ? "?offerId=" + offerId : "");
    }
}
