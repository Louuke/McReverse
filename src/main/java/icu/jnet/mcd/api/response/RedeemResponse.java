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
        return response.get("expirationTime");
    }

    public String getCode() {
        return response.get("randomCode");
    }
}
