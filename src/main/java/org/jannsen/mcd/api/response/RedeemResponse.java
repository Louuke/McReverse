package org.jannsen.mcd.api.response;

import org.jannsen.mcd.api.entity.redeem.Code;
import org.jannsen.mcd.api.response.status.Status;

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
