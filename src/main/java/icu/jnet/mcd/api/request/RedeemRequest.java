package icu.jnet.mcd.api.request;

public class RedeemRequest extends Request {

    private final int propositionId;
    private final long offerId;

    public RedeemRequest(int propositionId, long offerId) {
        this.propositionId = propositionId;
        this.offerId = offerId;
    }

    @Override
    public String getUrl() {
        String url = "https://eu-prod.api.mcd.com/exp/v1/offers/redemption/" + propositionId;
        return url + (offerId != 0 ? "?offerId=" + offerId : "");
    }
}
