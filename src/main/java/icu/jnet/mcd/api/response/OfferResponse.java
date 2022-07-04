package icu.jnet.mcd.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferResponse extends Response {

    private HashMap<String, List<Offer>> response;

    public List<Offer> getOffers() {
        return response != null ? response.get("offers") : new ArrayList<>();
    }

    public static class Offer {

        private Conditions conditions;
        private PunchInfo punchInfo;
        private RecurringInfo recurringInfo;
        private String CreationDateUtc, imageBaseLanguage, imageBaseName, localValidFrom, localValidTo, longDescription,
                name, offerBucket, shortDescription, validFromUTC, validToUTC;
        private boolean extendToEOD, isArchived, isDynamicExpiration, isLocked, isRedeemed, isSLPOffer, isvalidTotalOrder;
        private int colorCodingInfo, offerPropositionId, offerType, redemptionMode;
        private long offerId;

        public Conditions getConditions() {
            return conditions;
        }

        public PunchInfo getPunchInfo() {
            return punchInfo;
        }

        public RecurringInfo getRecurringInfo() {
            return recurringInfo;
        }

        public String getCreationDateUtc() {
            return CreationDateUtc;
        }

        public String getImageBaseLanguage() {
            return imageBaseLanguage;
        }

        public String getImageBaseName() {
            return imageBaseName;
        }

        public String getLocalValidFrom() {
            return localValidFrom;
        }

        public String getLocalValidTo() {
            return localValidTo;
        }

        public String getLongDescription() {
            return longDescription;
        }

        public String getName() {
            return name != null ? name.split("\n")[0].strip() : "";
        }

        public String getPrice() {
            return name != null ? name.split("\n")[1].strip() : "0";
        }

        public String getOfferBucket() {
            return offerBucket;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public String getValidFromUTC() {
            return validFromUTC;
        }

        public String getValidToUTC() {
            return validToUTC;
        }

        public String getImageUrl() {
            return "https://de-prod-us-cds-oceofferimages.s3.amazonaws.com/oce3-de-prod/offers/" + imageBaseName;
        }

        public boolean extendToEOD() {
            return extendToEOD;
        }

        public boolean isArchived() {
            return isArchived;
        }

        public boolean isDynamicExpiration() {
            return isDynamicExpiration;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public boolean isRedeemed() {
            return isRedeemed;
        }

        public boolean isSLPOffer() {
            return isSLPOffer;
        }

        public boolean isValidTotalOrder() {
            return isvalidTotalOrder;
        }

        public int getColorCodingInfo() {
            return colorCodingInfo;
        }

        public int getOfferPropositionId() {
            return offerPropositionId;
        }

        public int getOfferType() {
            return offerType;
        }

        public int getRedemptionMode() {
            return redemptionMode;
        }

        public long getOfferId() {
            return offerId;
        }
    }

    public static class Conditions {

        private List<String> dateConditions, dayOfWeekConditions, saleAmountConditions;

        public List<String> getDateConditions() {
            return dateConditions;
        }

        public List<String> getDayOfWeekConditions() {
            return dayOfWeekConditions;
        }

        public List<String> getSaleAmountConditions() {
            return saleAmountConditions;
        }
    }

    public static class PunchInfo {

        private int currentPunch, totalPunch;

        public int getCurrentPunch() {
            return currentPunch;
        }

        public int getTotalPunch() {
            return totalPunch;
        }
    }

    public static class RecurringInfo {

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
}
