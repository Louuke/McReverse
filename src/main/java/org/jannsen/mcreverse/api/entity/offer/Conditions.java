package org.jannsen.mcreverse.api.entity.offer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Conditions {

    @SerializedName("dateConditions") private List<String> dateConditions;
    @SerializedName("dayOfWeekConditions") private List<Object> dayOfWeekConditions;
    @SerializedName("saleAmountConditions") private List<SaleAmountConditions> saleAmountConditions;

    public List<String> getDateConditions() {
        return dateConditions != null ? dateConditions : new ArrayList<>();
    }

    public List<Object> getDayOfWeekConditions() {
        return dayOfWeekConditions != null ? dayOfWeekConditions : new ArrayList<>();
    }

    public List<SaleAmountConditions> getSaleAmountConditions() {
        return saleAmountConditions != null ? saleAmountConditions : new ArrayList<>();
    }

    public static class SaleAmountConditions {

        private boolean includeEligible, preTaxValidation, includeNonProduct, includeGiftCoupons;
        private int minimum;

        public boolean isIncludeEligible() {
            return includeEligible;
        }

        public boolean isIncludeGiftCoupons() {
            return includeGiftCoupons;
        }

        public boolean isIncludeNonProduct() {
            return includeNonProduct;
        }

        public boolean isPreTaxValidation() {
            return preTaxValidation;
        }

        public int getMinimum() {
            return minimum;
        }
    }
}
