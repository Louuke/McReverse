package icu.jnet.mcd.api.entity.offer;

import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;
import java.util.regex.Pattern;

import static icu.jnet.mcd.api.McClientSettings.ZONE_ID;

public class Offer {

    private static final Pattern pricePattern = Pattern.compile("\\d+,\\d\\d");
    private static final Pattern clockPattern = Pattern.compile("\\d\\d-\\d\\d");
    @SerializedName("conditions") private Conditions conditions;
    @SerializedName("punchInfo") private PunchInfo punchInfo;
    @SerializedName("recurringInfo") private RecurringInfo recurringInfo;
    @SerializedName("extendToEOD") private boolean extendToEOD;
    @SerializedName("isArchived") private boolean archived;
    @SerializedName("isDynamicExpiration") private boolean dynamicExpiration;
    @SerializedName("isLocked") private boolean locked;
    @SerializedName("isRedeemed") private boolean redeemed;
    @SerializedName("isSLPOffer") private boolean slpOffer;
    @SerializedName("isvalidTotalOrder") private boolean validTotalOrder;
    @SerializedName("imageBaseLanguage") private String imageBaseLanguage;
    @SerializedName("imageBaseName") private String imageBaseName;
    @SerializedName("localValidFrom") private String localValidFrom;
    @SerializedName("localValidTo") private String localValidTo;
    @SerializedName("longDescription") private String longDescription;
    @SerializedName("offerBucket") private String offerBucket;
    @SerializedName("shortDescription") private String shortDescription;
    @SerializedName("CreationDatedfdUtc") private String creationDateUTC;
    @SerializedName("name") private String fullName;
    @SerializedName("colorCodingInfo") private int colorCodingInfo;
    @SerializedName("offerPropositionId") private int offerPropositionId;
    @SerializedName("offerType") private int offerType;
    @SerializedName("redemptionMode") private int redemptionMode;
    @SerializedName("offerId") private long offerId;
    private long validFromUnix, validToUnix;
    private String shortName;
    private String price;

    public Conditions getConditions() {
        return conditions;
    }

    public PunchInfo getPunchInfo() {
        return punchInfo;
    }

    public RecurringInfo getRecurringInfo() {
        return recurringInfo;
    }

    public String getCreationDateUTC() {
        return creationDateUTC;
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

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getPrice() {
        return price;
    }

    public String getOfferBucket() {
        return offerBucket;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getImageUrl() {
        return "https://de-prod-us-cds-oceofferimages.s3.amazonaws.com/oce3-de-prod/offers/" + imageBaseName;
    }

    public Integer getPriceCents() {
        return pricePattern.matcher(price).results()
                .map(result -> result.group().replace(",", ""))
                .map(Integer::parseInt).findAny().orElse(0);
    }

    public Integer getAvailableHourFrom() {
        return clockPattern.matcher(price).results()
                .map(result -> Integer.parseInt(result.group().split("-")[0])).findAny().orElse(0);
    }

    public Integer getAvailableHourTo() {
        return clockPattern.matcher(getPrice()).results()
                .map(result -> Integer.parseInt(result.group().split("-")[1])).findAny().orElse(24);
    }

    public boolean isAvailable() {
        return getAvailableHourFrom() <= LocalTime.now(ZONE_ID).getHour()
                && LocalTime.now(ZONE_ID).getHour() < getAvailableHourTo();
    }

    public boolean hasUsesLeft() {
        int dayRedemption = getRecurringInfo().getCurrentDayRedemptionQuantity();
        int maxRedemption = getRecurringInfo().getMaxRedemptionQuantityPerDay();
        return maxRedemption == 0 || dayRedemption < maxRedemption;
    }

    public boolean isExtendToEOD() {
        return extendToEOD;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isDynamicExpiration() {
        return dynamicExpiration;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public boolean isSlpOffer() {
        return slpOffer;
    }

    public boolean isValidTotalOrder() {
        return validTotalOrder;
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

    public long getValidFromUnix() {
        return validFromUnix;
    }

    public long getValidToUnix() {
        return validToUnix;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Offer offer)) {
            return false;
        }
        return offer.getOfferPropositionId() == offerPropositionId;
    }

    @Override
    public int hashCode() {
        return offerPropositionId;
    }
}
