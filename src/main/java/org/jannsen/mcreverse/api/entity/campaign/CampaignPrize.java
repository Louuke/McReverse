package org.jannsen.mcreverse.api.entity.campaign;

import com.google.gson.annotations.SerializedName;

public class CampaignPrize {

    @SerializedName(value = "prizeId", alternate = "couponId")
    private String prizeId;
    private String beforeHeadline, buttonText, buttonUrl, headline, imageUrl, subheadline;
    private boolean isAddressFormSent;

    public String getBeforeHeadline() {
        return beforeHeadline;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public String getHeadline() {
        return headline;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSubHeadline() {
        return subheadline;
    }

    public boolean isAddressFormSent() {
        return isAddressFormSent;
    }
}
