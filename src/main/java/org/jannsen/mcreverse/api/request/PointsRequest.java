package org.jannsen.mcreverse.api.request;

public class PointsRequest extends Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/loyalty/customer/points";
    }
}
