package org.jannsen.mcreverse.api.entity.restaurant;

import java.util.List;

public class OpeningHours {

    private int dayOfWeekId;
    private List<Services> services;

    public int getDayOfWeekId() {
        return dayOfWeekId;
    }

    public List<Services> getServices() {
        return services;
    }
}
