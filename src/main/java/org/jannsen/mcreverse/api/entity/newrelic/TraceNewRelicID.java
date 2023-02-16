package org.jannsen.mcreverse.api.entity.newrelic;

import java.util.Base64;
import java.util.Random;

public class TraceNewRelicID implements TraceHeader {

    @Override
    public String getHeaderName() {
        return "x-newrelic-id";
    }

    @Override
    public String getHeaderValue() {
        byte[] bytes = new byte[16];
        new Random().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
