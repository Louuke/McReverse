package org.jannsen.mcreverse.api.request;

public class CampaignParticipateRequest extends CampaignRequest {

    private final transient String campaign;

    public CampaignParticipateRequest(String campaign, String formId, String token, String userId, String email) {
        super(formId, token, userId, email);
        this.campaign = campaign;
    }

    @Override
    public String getUrl() {
        return String.format("https://gma-prod.mcdonalds.de/service/%s/participate/", campaign);
    }
}
