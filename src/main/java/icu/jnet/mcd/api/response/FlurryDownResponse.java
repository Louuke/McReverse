package icu.jnet.mcd.api.response;

import java.util.ArrayList;
import java.util.List;

public class FlurryDownResponse extends Response {

    private String id;
    private List<String> participations;

    public String getId() {
        return id;
    }

    public List<String> getParticipations() {
        return participations != null ? participations : new ArrayList<>();
    }
}
