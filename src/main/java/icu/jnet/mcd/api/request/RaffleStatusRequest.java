package icu.jnet.mcd.api.request;

public class RaffleStatusRequest implements Request {

    private final String formId = "bigmac2022", token, userId, userName;

    public RaffleStatusRequest(String token, String userName, String userId) {
        this.token = token;
        this.userName = userName;
        this.userId = userId;
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/raffle/status";
    }
}
