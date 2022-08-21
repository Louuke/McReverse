package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.HashMap;

public class BasicBearerResponse extends Response {

    private HashMap<String, Object> response;

    public BasicBearerResponse(Status status) {
        super(status);
    }

    public int getExpires() {
        return response != null ? (int) response.get("expires") : 0;
    }

    public String getToken() {
        return response != null ? (String) response.get("token") : "";
    }
}
