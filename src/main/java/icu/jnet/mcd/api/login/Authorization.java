package icu.jnet.mcd.api.login;

import java.util.Objects;

public class Authorization {

    private String accessToken, refreshToken;

    public String getAccessToken() {
        return accessToken != null ? "Bearer " + accessToken : "";
    }

    public String getRefreshToken() {
        return refreshToken != null ? refreshToken : "";
    }

    public String getBareToken() {
        return getAccessToken().replace("Bearer ", "");
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
