package org.jannsen.mcd.api.response;

import org.jannsen.mcd.api.response.status.Status;

public class Response {

    private final Status status;

    public Response(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status != null ? status : new Status();
    }

    public boolean success() {
        return getStatus().getType().equals("Success");
    }
}
