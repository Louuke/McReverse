package icu.jnet.mcd.api.entity.bonus;

public class BonusPoints {

    private String bonusType, campaignEndDate, campaignStartDate, imageURL, offerValue, offerValueType,
            termsAndConditions, title;
    private int campaignId;
    private boolean optInRequired, optedIn;

    public String getBonusType() {
        return bonusType;
    }

    public String getCampaignEndDate() {
        return campaignEndDate;
    }

    public String getCampaignStartDate() {
        return campaignStartDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getOfferValue() {
        return offerValue;
    }

    public String getOfferValueType() {
        return offerValueType;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public String getTitle() {
        return title;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public boolean isOptInRequired() {
        return optInRequired;
    }

    public boolean isOptedIn() {
        return optedIn;
    }
}
