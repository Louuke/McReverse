package icu.jnet.mcd.api.request;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CalendarStateRequest implements Request {

    private final String userId, email, token, deviceId;

    public CalendarStateRequest(String userId, String email, String token, String deviceId) {
        this.userId = userId;
        this.email = email;
        this.token = token;
        this.deviceId = deviceId;
    }

    @Override
    public String getUrl() {
        String url = "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/appcalendar/getUserState?userId=%s&token=%s&userName=%s&deviceId=%s&formId=";
        return String.format(url, userId, token, URLEncoder.encode(email, StandardCharsets.UTF_8), deviceId);
    }
}
