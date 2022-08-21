package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.ArrayList;

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
