package icu.jnet.mcd.api;

import icu.jnet.mcd.network.RequestManager;

import java.time.ZoneId;

public class McClientSettings {

    public static ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

    public static void setRequestsPerSecond(double rps) {
        RequestManager.getInstance().setRequestsPerSecond(rps);
    }
}
