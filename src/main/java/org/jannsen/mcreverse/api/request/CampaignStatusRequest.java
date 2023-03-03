package org.jannsen.mcreverse.api.request;

public class CampaignStatusRequest extends CampaignRequest {

    private final transient String campaign;

    public CampaignStatusRequest(String campaign, String formId, String token, String userId, String email) {
        super(formId, token, userId, email);
        this.campaign = campaign;
    }

    @Override
    public String getUrl() {
        return String.format("https://gma-prod.mcdonalds.de/service/%s/status", campaign);
    }
}
