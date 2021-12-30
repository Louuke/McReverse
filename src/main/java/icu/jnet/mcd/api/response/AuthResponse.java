package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class AuthResponse extends Response {

    private HashMap<String, Object> response;

    public int getExpires() {
        return (int) response.get("expires");
    }

    public String getToken() {
        return (String) response.get("token");
    }
}
