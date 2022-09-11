package icu.jnet.mcd.api.request;

public class OptInRequest extends Request {

    private final int campaignId;

    public OptInRequest(int campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/loyalty/customer/campaign/optIn";
    }
}
