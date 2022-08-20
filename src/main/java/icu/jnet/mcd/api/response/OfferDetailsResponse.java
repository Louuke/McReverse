package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.entity.offer.Conditions;
import icu.jnet.mcd.api.response.status.Status;

import java.util.ArrayList;
import java.util.List;

public class OfferDetailsResponse extends Response {

    private Response response;

    public OfferDetailsResponse(Status status) {
        super(status);
    }

    public Response getResponse() {
        return response;
    }

    public static class Response {

        private int colorCodingInfo, offerPropositionId, offerType, orderDiscountType, redemptionMode;
        private boolean isDynamicExpiration, isExpired, isLocked, isSLPOffer, isvalidTotalOrder;
        private String imageBaseLanguage, imageBaseName, localValidFrom, localValidTo, longDescription, name, offerBucket;
        private Conditions conditions;
        private RecurringInfo recurringInfo;
        private List<ProductSet> productSets;
        private List<String> restaurants;

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

        public int getOrderDiscountType() {
            return orderDiscountType;
        }

        public boolean isSLPOffer() {
            return isSLPOffer;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public boolean isDynamicExpiration() {
            return isDynamicExpiration;
        }

        public boolean isExpired() {
            return isExpired;
        }

        public boolean isValidTotalOrder() {
            return isvalidTotalOrder;
        }

        public String getImageBaseLanguage() {
            return imageBaseLanguage;
        }

        public String getImageBaseName() {
            return imageBaseName;
        }

        public String getOfferBucket() {
            return offerBucket;
        }

        public String getName() {
            return name != null ? name.split("\n")[0].strip() : "";
        }

        public String getPrice() {
            return name != null ? name.split("\n")[1].strip() : "0";
        }

        public String getImageUrl() {
            return "https://de-prod-us-cds-oceofferimages.s3.amazonaws.com/oce3-de-prod/offers/" + imageBaseName;
        }

        public String getLongDescription() {
            return longDescription;
        }

        public String getLocalValidTo() {
            return localValidTo;
        }

        public String getLocalValidFrom() {
            return localValidFrom;
        }

        public Conditions getConditions() {
            return conditions;
        }

        public RecurringInfo getRecurringInfo() {
            return recurringInfo;
        }

        public List<ProductSet> getProductSets() {
            return productSets != null ? productSets : new ArrayList<>();
        }

        public List<String> getRestaurants() {
            return restaurants != null ? restaurants : new ArrayList<>();
        }
    }

    public static class ProductSet {
        private int minQuantity, quantity;
        private String alias;
        private Action action;
        private List<String> products;

        public Action getAction() {
            return action;
        }

        public String getAlias() {
            return alias;
        }

        public int getMinQuantity() {
            return minQuantity;
        }

        public List<String> getProducts() {
            return products != null ? products : new ArrayList<>();
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public static class Action {
        private int discountType, type;
        private double value;

        public int getDiscountType() {
            return discountType;
        }

        public int getType() {
            return type;
        }

        public double getValue() {
            return value;
        }
    }

    public static class RecurringInfo {

        private int maxRedemptionQuantity, maxRedemptionQuantityPerDay, maxRedemptionQuantityPerMonth,
                maxRedemptionQuantityPerWeek;

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
