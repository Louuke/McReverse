package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.redeem.Code;
import org.jannsen.mcreverse.api.response.status.Status;

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
