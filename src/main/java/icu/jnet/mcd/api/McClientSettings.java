package icu.jnet.mcd.api;

import icu.jnet.mcd.network.RequestManager;

import java.net.Proxy;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class McClientSettings {

    private static List<Proxy> mProxies = new ArrayList<>();

    static {
        mProxies.add(null);
    }

    public static ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

    public static void setRequestsPerSecond(double rps) {
        RequestManager.getInstance().setRequestsPerSecond(rps);
    }

    public static void setProxies(List<Proxy> proxies) {
        mProxies = proxies;
    }

    public static List<Proxy> getProxies() {
        return mProxies;
    }
}
