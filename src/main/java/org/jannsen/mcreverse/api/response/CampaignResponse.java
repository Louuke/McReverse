package org.jannsen.mcreverse.api.response;

import com.google.gson.annotations.SerializedName;
import org.jannsen.mcreverse.api.entity.campaign.CampaignPrize;
import org.jannsen.mcreverse.api.response.status.Status;

public class CampaignResponse extends Response {

    @SerializedName(value = "prize", alternate = "coupon")
    private CampaignPrize prize;
    private boolean participated;
    private String success, userId;

    public CampaignResponse(Status status) {
        super(status);
    }

    public CampaignPrize getPrize() {
        return prize;
    }

    public boolean isParticipated() {
        return participated;
    }

    public boolean success() {
        return success != null && success.equals("ok");
    }

    public String getUserId() {
        return userId;
    }
}
