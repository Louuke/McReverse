package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class RedeemResponse extends Response {

    private HashMap<String, String> response;

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
