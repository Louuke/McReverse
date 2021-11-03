package icu.jnet.mcd.auth;

public class Authorization {

    private String accessToken, refreshToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = "Bearer " + accessToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken != null ? accessToken : "";
    }

    public String getRefreshToken() {
        return refreshToken != null ? accessToken : "";
    }
}
