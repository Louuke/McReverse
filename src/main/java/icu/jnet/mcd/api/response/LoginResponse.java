package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.api.response.status.Status;

public class LoginResponse extends Response {

    private Authorization response;

    public LoginResponse(Status status) {
        super(status);
    }

    public Authorization getResponse() {
        return response;
    }
}
