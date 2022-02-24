package icu.jnet.mcd.api.request;

import java.util.HashMap;

public class EasterRequest implements Request {

    private HashMap<String, String> data;
    private final String formId = "arPage", token, userName, userId;
    private String deviceId;

    public EasterRequest(String token, String userName, String userId, String deviceId, String id) {
        this(token, userName, userId);
        this.deviceId = deviceId;
        this.data = new HashMap<>();
        this.data .put("id", id);
    }

    public EasterRequest(String token, String userName, String userId) {
        this.token = token;
        this.userName = userName;
        this.userId = userId;
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/easter/foundEgg";
    }
}
