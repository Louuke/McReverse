package icu.jnet.mcd.api.request;

import java.util.HashMap;
import java.util.Map;

public class RafflePartRequest extends RaffleStatusRequest {

    private final Map<String, Object> data = new HashMap<>();
    private final String deviceId, raffle = "bigmac2022";

    public RafflePartRequest(String token, String userName, String userId, String deviceId) {
        super(token, userName, userId);
        this.deviceId = deviceId;
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/upload";
    }
}
