package org.jannsen.mcreverse;

import org.jannsen.mcreverse.api.entity.akamai.SensorToken;

import java.util.function.Supplier;

public class SensorFetcher implements Supplier<SensorToken> {

    @Override
    public SensorToken get() {
        // QUERY TOKEN
        return new SensorToken();
    }
}
