package org.jannsen.mcreverse.api;

import org.jannsen.mcreverse.network.RequestScheduler;

import java.time.ZoneId;

public class McClientSettings {

    public static ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

    public static void setRequestsPerSecond(double rps) {
        RequestScheduler.getInstance().setRequestsPerSecond(rps);
    }
}
