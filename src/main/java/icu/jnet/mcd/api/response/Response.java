package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

public class Response {

    private Status status;

    public Response(Status status) {
        this.status = status;
    }

    public Response() {}

    public Status getStatus() {
        return status;
    }
}
