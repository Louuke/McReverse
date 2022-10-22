package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.login.Authorization;
import org.jannsen.mcreverse.api.response.status.Status;

public class LoginResponse extends Response {

    private Authorization response;

    public LoginResponse(Status status) {
        super(status);
    }

    public Authorization getResponse() {
        return response;
    }
}
