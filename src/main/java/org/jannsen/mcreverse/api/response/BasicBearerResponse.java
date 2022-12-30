package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.auth.BasicBearerAuthorization;
import org.jannsen.mcreverse.api.response.status.Status;

public class BasicBearerResponse extends Response {

    private BasicBearerAuthorization response;

    public BasicBearerResponse(Status status) {
        super(status);
    }

    public BasicBearerAuthorization getResponse() {
        return response != null ? response : new BasicBearerAuthorization();
    }
}
