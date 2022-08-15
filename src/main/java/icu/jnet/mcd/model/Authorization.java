package icu.jnet.mcd.model;

import java.time.Instant;
import java.util.Objects;

public class Authorization extends StateChangeable {

    private String accessToken, refreshToken;
    private long lastUpdate = Instant.now().getEpochSecond();

    public void updateAccessToken(String accessToken, boolean notify) {
        if(accessToken == null) {
            return;
        }
        this.accessToken = "Bearer " + accessToken;
        if(notify) {
            this.lastUpdate = Instant.now().getEpochSecond();
            super.notifyListeners(this);
        }
    }

    public void updateRefreshToken(String refreshToken) {
        if(refreshToken == null) {
            return;
        }
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken != null ? accessToken : "";
    }

    public String getRefreshToken() {
        return refreshToken != null ? refreshToken : "";
    }

    public String getBareToken() {
        return getAccessToken().replace("Bearer ", "");
    }

    public long getLastUpdate() {
        return lastUpdate;
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
