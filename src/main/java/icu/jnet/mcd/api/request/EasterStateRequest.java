package icu.jnet.mcd.api.request;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EasterStateRequest implements Request {

    private final String userId, email, token, deviceId;

    public EasterStateRequest(String userId, String email, String token, String deviceId) {
        this.userId = userId;
        this.email = email;
        this.token = token;
        this.deviceId = deviceId;
    }

    @Override
    public String getUrl() {
        String url = "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/easter/getUserState?"
                + "userId=%s"
                + "&token=%s"
                + "&userName=%s"
                + "&deviceId=%s"
                + "&formId=calendar1";
        return String.format(url, userId, token, URLEncoder.encode(email, StandardCharsets.UTF_8), deviceId);
    }
}
