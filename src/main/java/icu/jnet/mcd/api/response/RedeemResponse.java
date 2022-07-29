package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.HashMap;

public class RedeemResponse extends Response {

    private HashMap<String, String> response;

    public RedeemResponse(Status status) {
        super(status);
    }

    public String getBarCodeContent() {
        return response.get("barCodeContent");
    }

    public String getExpirationTime() {
        return response != null ? response.get("expirationTime") : "1970-01-01T00:00:00Z";
    }

    public String getCode() {
        return response != null ? response.get("randomCode") : "";
    }
}
