package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.entity.profile.CustomerInformation;
import icu.jnet.mcd.api.request.ProfileRequest;
import icu.jnet.mcd.api.response.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileResponse extends Response {

    private Map<String, CustomerInformation> response;

    public ProfileResponse(Status status) {
        super(status);
    }

    public CustomerInformation getResponse() {
        return response != null ? response.get("customerInformation") : new CustomerInformation();
    }
}
