package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.HashMap;
import java.util.Map;

public class PointsResponse extends Response {

    private Map<String, Double> response;

    public PointsResponse(Status status) {
        super(status);
    }

    public double getTotalPoints() {
        return response != null ? response.get("totalPoints") : 0;
    }

    public double getLifeTimePoints() {
        return response != null ? response.get("lifeTimePoints") : 0;
    }

    @Override
    public boolean success() {
        return getStatus().getType().equals("Absolute Success");
    }
}
