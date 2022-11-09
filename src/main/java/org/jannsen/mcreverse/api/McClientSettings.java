package org.jannsen.mcreverse.api;

import org.jannsen.mcreverse.network.RequestManager;

import java.time.ZoneId;

public class McClientSettings {

    public static ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

    public static void setRequestsPerSecond(double rps) {
        RequestManager.getInstance().setRequestsPerSecond(rps);
    }
}
