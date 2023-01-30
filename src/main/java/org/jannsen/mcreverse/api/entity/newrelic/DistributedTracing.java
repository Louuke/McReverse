package org.jannsen.mcreverse.api.entity.newrelic;

import java.util.UUID;

public class DistributedTracing {

    public static String generateRandomBytes(int length) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < Math.nextUp(length / 28.0); i++) {
            builder.append(UUID.randomUUID().toString().replace("-", ""));
        }
        return builder.substring(0, length);
    }

    public static String generateSpanId() {
        return generateRandomBytes(16);
    }

    public static String generateTraceId() {
        return generateRandomBytes(32);
    }
}
