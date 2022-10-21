package org.jannsen.mcd.api.response;

import org.jannsen.mcd.api.entity.login.Authorization;
import org.jannsen.mcd.api.response.status.Status;

public class LoginResponse extends Response {

    private Authorization response;

    public LoginResponse(Status status) {
        super(status);
    }

    public Authorization getResponse() {
        return response;
    }
}
