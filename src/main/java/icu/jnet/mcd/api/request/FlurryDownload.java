package icu.jnet.mcd.api.request;

public class FlurryDownload implements Request {

    private final String userId, formId = "mcflurryparookaville2022", token, userName, deviceId;

    public FlurryDownload(String userId, String token, String userName, String deviceId) {
        this.userId = userId;
        this.token = token;
        this.userName = userName;
        this.deviceId = deviceId;
    }

    @Override
    public String getUrl() {
        return String.format("https://mcd-gmarest-prod.mcdonalds.de/mcd-gmarestservice/service/download"
                + "?userId=%s&formId=%s&token=%s&userName=%s&deviceId=%s", userId, formId, token, userName, deviceId);
    }
}
