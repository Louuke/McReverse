package org.jannsen.mcreverse.api.entity.newrelic;

import java.util.Locale;

public class TraceParent implements TraceHeader {

    private final String parentId = DistributedTracing.generateSpanId();
    private final TraceContext context;

    public TraceParent(TraceContext context) {
        this.context = context;
    }

    public String getVersion() {
        return "00";
    }

    public String getParentId() {
        return parentId;
    }

    @Override
    public String getHeaderName() {
        return "traceparent";
    }

    @Override
    public String getHeaderValue() {
        return String.format(Locale.ROOT, "%s-%s-%s-%s", getVersion(), context.getTraceState().getTraceId(),
                getParentId(), context.getSampled());
    }
}
