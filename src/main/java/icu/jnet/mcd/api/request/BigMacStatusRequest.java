package icu.jnet.mcd.api.request;

public class BigMacStatusRequest implements Request {

    private final String formId = "BIGMAC2022_1", token, userId, userName;

    public BigMacStatusRequest(String token, String userName, String userId) {
        this.token = token;
        this.userName = userName;
        this.userId = userId;
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/bigmac2022/status";
    }
}
