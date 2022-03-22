package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class PointsResponse extends Response {

    private HashMap<String, Double> response;

    public double getTotalPoints() {
        return response != null ? response.get("totalPoints") : 0;
    }

    public double getLifeTimePoints() {
        return response != null ? response.get("lifeTimePoints") : 0;
    }

}
