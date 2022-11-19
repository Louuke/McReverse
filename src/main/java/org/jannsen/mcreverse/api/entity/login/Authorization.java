package org.jannsen.mcreverse.api.entity.login;

import com.google.gson.annotations.SerializedName;
import org.jannsen.mcreverse.annotation.Auth;

import java.time.Instant;
import java.util.Objects;

public abstract class Authorization {

    private @SerializedName(value = "accessToken", alternate = {"token"}) String accessToken;
    private long createdUnix = Instant.now().getEpochSecond();

    public String getAccessToken() {
        return accessToken != null ? accessToken : "";
    }

    public String getAccessToken(boolean withPrefix) {
        return !withPrefix ? getAccessToken() : getAuthType().prefix() + " " + getAccessToken();
    }

    public long getCreatedUnix() {
        return createdUnix;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setCreatedUnix(long createdUnix) {
        this.createdUnix = createdUnix;
    }

    abstract Auth.Type getAuthType();

    @Override
    public int hashCode() {
        return Objects.hash(accessToken);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Authorization auth)) {
            return false;
        }
        return auth.getAccessToken().equals(accessToken);
    }
}
