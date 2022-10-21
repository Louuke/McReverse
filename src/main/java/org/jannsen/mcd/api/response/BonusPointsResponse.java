package org.jannsen.mcd.api.response;

import org.jannsen.mcd.api.entity.bonus.BonusPoints;
import org.jannsen.mcd.api.response.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BonusPointsResponse extends Response {

    private HashMap<String, List<BonusPoints>> response;

    public BonusPointsResponse(Status status) {
        super(status);
    }

    public List<BonusPoints> getBonuses() {
        return response != null ? response.get("bonusPoints") : new ArrayList<>();
    }

    @Override
    public boolean success() {
        return getStatus().getType().equals("Absolute Success");
    }
}
