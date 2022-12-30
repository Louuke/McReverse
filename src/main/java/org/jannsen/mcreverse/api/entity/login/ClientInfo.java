package org.jannsen.mcreverse.api.entity.login;

import java.util.HashMap;
import java.util.Map;

public class ClientInfo {

    private final Map<String, String> device = new HashMap<>();

    public ClientInfo(String deviceId) {
        device.put("deviceUniqueId", deviceId);
        device.put("os", "android");
        device.put("osVersion", "11");
    }
}
