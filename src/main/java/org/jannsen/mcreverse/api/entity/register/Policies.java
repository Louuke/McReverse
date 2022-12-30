package org.jannsen.mcreverse.api.entity.register;

import java.util.HashMap;

public class Policies {

    private final HashMap<String, Boolean> acceptancePolicies = new HashMap<>();

    public Policies() {
        acceptancePolicies.put("1", true);
        acceptancePolicies.put("4", true);
    }
}
