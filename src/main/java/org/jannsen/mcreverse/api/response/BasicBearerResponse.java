package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.login.BasicBearerAuthorization;
import org.jannsen.mcreverse.api.entity.login.BearerAuthorization;
import org.jannsen.mcreverse.api.response.status.Status;

import java.util.HashMap;

public class BasicBearerResponse extends Response {

    private BasicBearerAuthorization response;

    public BasicBearerResponse(Status status) {
        super(status);
    }

    public BasicBearerAuthorization getResponse() {
        return response != null ? response : new BasicBearerAuthorization();
    }
}
