package icu.jnet.mcd.api.login;

import java.util.Objects;

public class Authorization {

    private final String accessToken = "", refreshToken = "";

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Authorization)) {
            return false;
        }
        Authorization auth = (Authorization) obj;
        return auth.getAccessToken().equals(accessToken) && auth.getRefreshToken().equals(refreshToken);
    }
}
