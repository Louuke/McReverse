package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class AuthResponse extends Response {

    private HashMap<String, Object> response;

    public int getExpires() {
        return response != null ? (int) response.get("expires") : 0;
    }

    public String getToken() {
        return response != null ? (String) response.get("token") : "";
    }
}
