package icu.jnet.mcd.api.request;

public class RefreshRequest implements Request {

    private final String refreshToken;

    public RefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/login/refresh";
    }
}
