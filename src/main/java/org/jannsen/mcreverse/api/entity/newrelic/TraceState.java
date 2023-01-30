package org.jannsen.mcreverse.api.entity.newrelic;

import java.util.Locale;

public class TraceState implements TraceHeader {

    private final String traceId = DistributedTracing.generateTraceId();
    private final TraceContext context;

    public TraceState(TraceContext context) {
        this.context = context;
    }

    public String getTraceId() {
        return traceId;
    }

    @Override
    public String getHeaderName() {
        return "tracestate";
    }

    @Override
    public String getHeaderValue() {
        return context.getVendor() + "=" + getVendorState();
    }

    public String getVendorState() {
        return String.format(Locale.ROOT, "%1d-%1d-%s-%s-%s-%s-%s-%s-%d", 0, 2, context.getAccountId(),
                context.getApplicationId(), context.getTraceParent().getParentId(), "", "", "", System.currentTimeMillis());
    }
}
