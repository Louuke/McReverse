package org.jannsen.mcreverse.api.entity.auth;

import org.jannsen.mcreverse.annotation.Auth;

public class BasicBearerAuthorization extends Authorization {

    private int expires;

    public int getExpires() {
        return expires;
    }

    @Override
    Auth.Type getAuthType() {
        return Auth.Type.BasicBearer;
    }
}
