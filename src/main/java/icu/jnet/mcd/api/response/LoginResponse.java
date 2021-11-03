package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class LoginResponse extends Response {

    private HashMap<String, String> response;

    public String getAccessToken() {
        return response != null ? response.getOrDefault("accessToken", null) : null;
    }

    public String getRefreshToken() {
        return response != null ? response.getOrDefault("refreshToken", null) : null;
    }
}
