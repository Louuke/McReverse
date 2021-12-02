package icu.jnet.mcd.api.request;

public class RefreshRequest {

    private final String refreshToken;

    public RefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
