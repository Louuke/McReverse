package org.jannsen.mcreverse.api.entity.auth;

import org.jannsen.mcreverse.annotation.Auth;

public class BasicAuthorization extends Authorization{

    @Override
    public String getAccessToken() {
        return "NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";
    }

    @Override
    Auth.Type getAuthType() {
        return Auth.Type.Basic;
    }
}
