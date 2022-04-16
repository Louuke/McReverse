package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.ArrayList;

public class Response {

    private Status status;

    public Response(Status status) {
        this.status = status;
    }

    public Response() {}

    public Status getStatus() {
        return status != null ? status : new Status("IOException", "Error", new ArrayList<>());
    }
}
