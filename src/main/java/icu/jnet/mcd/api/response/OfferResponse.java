package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.entity.response.Offer;
import icu.jnet.mcd.api.response.status.Status;

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
