package org.jannsen.mcreverse.api.entity.offer;

import java.util.ArrayList;
import java.util.List;

public class Conditions {

    private List<String> dateConditions, dayOfWeekConditions, saleAmountConditions;

    public List<String> getDateConditions() {
        return dateConditions != null ? dateConditions : new ArrayList<>();
    }

    public List<String> getDayOfWeekConditions() {
        return dayOfWeekConditions != null ? dayOfWeekConditions : new ArrayList<>();
    }

    public List<String> getSaleAmountConditions() {
        return saleAmountConditions != null ? saleAmountConditions : new ArrayList<>();
    }
}
