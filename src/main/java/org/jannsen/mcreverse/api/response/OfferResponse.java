package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.offer.Offer;
import org.jannsen.mcreverse.api.response.status.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OfferResponse extends Response {

    private Map<String, List<Offer>> response;

    public OfferResponse(Status status) {
        super(status);
    }

    public List<Offer> getResponse() {
        return response != null ? response.get("offers") : new ArrayList<>();
    }

    @Override
    public boolean success() {
        return getStatus().getType().equals("Absolute Success");
    }
}
