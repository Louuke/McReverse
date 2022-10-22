package org.jannsen.mcreverse.api.entity.login;

import java.time.Instant;
import java.util.Objects;

public class Authorization {

    private String accessToken, refreshToken;
    private long createdUnix = Instant.now().getEpochSecond();

    public String getAccessToken() {
        return accessToken != null ? accessToken : "";
    }

    public String getRefreshToken() {
        return refreshToken != null ? refreshToken : "";
    }

    public long getCreatedUnix() {
        return createdUnix;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setCreatedUnix(long createdUnix) {
        this.createdUnix = createdUnix;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Authorization auth)) {
            return false;
        }
        return auth.getAccessToken().equals(accessToken) && auth.getRefreshToken().equals(refreshToken);
    }
}
