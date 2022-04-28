package icu.jnet.mcd.api.request;

public class BonusPointsRequest implements Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v3/loyalty/customer/bonus/points";
    }
}
