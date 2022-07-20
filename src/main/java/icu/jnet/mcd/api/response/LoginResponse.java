package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.HashMap;

public class LoginResponse extends Response {

    private HashMap<String, String> response;

    public LoginResponse(Status status) {
        super(status);
    }

    public String getAccessToken() {
        return response != null ? response.getOrDefault("accessToken", null) : null;
    }

    public String getRefreshToken() {
        return response != null ? response.getOrDefault("refreshToken", null) : null;
    }
}
