package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.profile.CustomerInformation;
import org.jannsen.mcreverse.api.response.status.Status;

import java.util.Arrays;
import java.util.Map;

public class ProfileResponse extends Response {

    private Map<String, CustomerInformation> response;

    public ProfileResponse(Status status) {
        super(status);
    }

    @Override
    public CustomerInformation getResponse() {
        return response != null ? response.get("customerInformation") : new CustomerInformation();
    }

    public boolean usesMyMcDonalds() {
        return getResponse().getSubscriptions().stream()
                .filter(sub -> sub.getOptInStatus().equals("Y")
                        && Arrays.asList("23", "24", "25").contains(sub.getSubscriptionId())
                        || sub.getOptInStatus().equals("N")
                        && sub.getSubscriptionId().equals("21")).count() == 4;
    }
}
