package icu.jnet.mcd.api.request;

import java.util.HashMap;
import java.util.Map;

public class FlurryUpload implements Request {

    private final Map<String, String> data = new HashMap<>();
    private final String deviceId, formId = "mcflurryparookaville2022", raffle = "mcflurryparookaville2022",
        token, userId, userName;

    public FlurryUpload(String deviceId, String token, String userId, String userName) {
        this.deviceId = deviceId;
        this.token = token;
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String getUrl() {
        return "https://mcd-gmarest-prod.mcdonalds.de/mcd-gmarestservice/service/upload";
    }
}
