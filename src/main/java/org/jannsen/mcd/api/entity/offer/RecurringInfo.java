package org.jannsen.mcd.api.entity.offer;

public class RecurringInfo {

    private int currentDayRedemptionQuantity, currentMonthRedemptionQuantity, currentWeekRedemptionQuantity,
            totalRedemptionQuantity, maxRedemptionQuantity, maxRedemptionQuantityPerDay, maxRedemptionQuantityPerMonth,
            maxRedemptionQuantityPerWeek;

    public int getCurrentDayRedemptionQuantity() {
        return currentDayRedemptionQuantity;
    }

    public int getCurrentMonthRedemptionQuantity() {
        return currentMonthRedemptionQuantity;
    }

    public int getCurrentWeekRedemptionQuantity() {
        return currentWeekRedemptionQuantity;
    }

    public int getTotalRedemptionQuantity() {
        return totalRedemptionQuantity;
    }

    public int getMaxRedemptionQuantity() {
        return maxRedemptionQuantity;
    }

    public int getMaxRedemptionQuantityPerDay() {
        return maxRedemptionQuantityPerDay;
    }

    public int getMaxRedemptionQuantityPerMonth() {
        return maxRedemptionQuantityPerMonth;
    }

    public int getMaxRedemptionQuantityPerWeek() {
        return maxRedemptionQuantityPerWeek;
    }
}
