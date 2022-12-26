package org.jannsen.mcreverse.listener;

import org.jannsen.mcreverse.SensorFetcher;
import org.jannsen.mcreverse.api.entity.akamai.SensorToken;
import org.jannsen.mcreverse.utils.listener.ClientActionListener;

public class ClientListener implements ClientActionListener {

    @Override
    public SensorToken tokenRequired() {
        return SensorFetcher.queryToken();
    }
}
