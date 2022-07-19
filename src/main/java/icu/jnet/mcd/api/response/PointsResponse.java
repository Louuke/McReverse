package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.HashMap;

public class PointsResponse extends Response {

    private HashMap<String, Double> response;

    public PointsResponse(Status status) {
        super(status);
    }

    public double getTotalPoints() {
        return response != null ? response.get("totalPoints") : 0;
    }

    public double getLifeTimePoints() {
        return response != null ? response.get("lifeTimePoints") : 0;
    }

}
