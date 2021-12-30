package icu.jnet.mcd.api.request;

public class PointsRequest implements Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/loyalty/customer/points";
    }
}
