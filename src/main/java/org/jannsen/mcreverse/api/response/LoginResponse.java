package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.login.BearerAuthorization;
import org.jannsen.mcreverse.api.response.status.Status;

public class LoginResponse extends Response {

    private BearerAuthorization response;

    public LoginResponse(Status status) {
        super(status);
    }

    public BearerAuthorization getResponse() {
        return response != null ? response : new BearerAuthorization();
    }
}
