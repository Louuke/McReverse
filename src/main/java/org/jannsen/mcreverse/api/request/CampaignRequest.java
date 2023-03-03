package org.jannsen.mcreverse.api.request;

public abstract class CampaignRequest extends Request {

    private final String formId, token, userId, userName;

    public CampaignRequest(String formId, String token, String userId, String email) {
        this.formId = formId;
        this.token = token;
        this.userId = userId;
        this.userName = email;
    }
}
