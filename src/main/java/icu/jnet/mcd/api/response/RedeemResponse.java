package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.entity.response.Code;
import icu.jnet.mcd.api.response.status.Status;

public class RedeemResponse extends Response {

    private Code response;

    public RedeemResponse(Status status) {
        super(status);
    }

    public Code getResponse() {
        return response;
    }

    @Override
    public boolean success() {
        return getStatus().getType().equals("Absolute Success");
    }
}
