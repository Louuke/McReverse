package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.login.Authorization;
import icu.jnet.mcd.api.response.status.Status;

import java.util.HashMap;
import java.util.Map;

public class LoginResponse extends Response {

    private Authorization response;

    public LoginResponse(Status status) {
        super(status);
    }

    public Authorization getResponse() {
        return response;
    }
}
