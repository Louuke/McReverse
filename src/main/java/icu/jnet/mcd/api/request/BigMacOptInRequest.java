package icu.jnet.mcd.api.request;

public class BigMacOptInRequest implements Request {

    private int campaignId = 250;

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/loyalty/customer/campaign/optIn";
    }
}
