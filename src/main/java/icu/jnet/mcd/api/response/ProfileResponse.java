package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.entity.response.Profile;
import icu.jnet.mcd.api.response.status.Status;

import java.util.Map;

public class ProfileResponse extends Response {

    private Map<String, Profile> response;

    public ProfileResponse(Status status) {
        super(status);
    }

    public Profile getResponse() {
        return response != null ? response.get("customerInformation") : new Profile();
    }
}
