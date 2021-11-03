package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class PointsResponse extends Response {

    private HashMap<String, Double> response;

    public double getTotalPoints() {
        return response.get("totalPoints");
    }

    public double getLifeTimePoints() {
        return response.get("lifeTimePoints");
    }

}
