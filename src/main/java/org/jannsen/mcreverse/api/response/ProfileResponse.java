package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.profile.CustomerInformation;
import org.jannsen.mcreverse.api.response.status.Status;

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
