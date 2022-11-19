package org.jannsen.mcreverse.api.entity.login;

import com.google.gson.annotations.SerializedName;
import org.jannsen.mcreverse.annotation.Auth;

public class BearerAuthorization extends Authorization {

    @SerializedName("refreshToken") private String refreshToken;

    public String getRefreshToken() {
        return refreshToken != null ? refreshToken : "";
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    Auth.Type getAuthType() {
        return Auth.Type.Bearer;
    }
}
