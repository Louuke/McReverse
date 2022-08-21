package icu.jnet.mcd.api.entity.offer;

import com.google.gson.annotations.SerializedName;
import icu.jnet.mcd.api.entity.PojoEntity;

public class Offer extends PojoEntity {

    @SerializedName("conditions") private Conditions conditions;
    @SerializedName("punchInfo") private PunchInfo punchInfo;
    @SerializedName("recurringInfo") private RecurringInfo recurringInfo;
    @SerializedName("CreationDateUtc") private String creationDateUTC;
    @SerializedName("name") private String fullName;
    @SerializedName("extendToEOD") private boolean extendToEOD;
    @SerializedName("isArchived") private boolean archived;
    @SerializedName("isDynamicExpiration") private boolean dynamicExpiration;
    @SerializedName("isLocked") private boolean locked;
    @SerializedName("isRedeemed") private boolean redeemed;
    @SerializedName("isSLPOffer") private boolean slpOffer;
    @SerializedName("isvalidTotalOrder") private boolean validTotalOrder;
    private String imageBaseLanguage, imageBaseName, localValidFrom, localValidTo, longDescription, offerBucket,
            shortDescription, validFromUTC, validToUTC;
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

    public String getName() {
        return fullName != null ? fullName.split("\n")[0].strip() : "";
    }

    public String getPrice() {
        return fullName != null ? fullName.split("\n")[1].strip() : "0";
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

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Offer)) {
            return false;
        }
        Offer offer = (Offer) o;
        return offer.getOfferPropositionId() == offerPropositionId;
    }

    @Override
    public int hashCode() {
        return offerPropositionId;
    }
}
