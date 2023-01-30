package org.jannsen.mcreverse.api.entity.newrelic;

import java.util.Arrays;
import java.util.List;

public class TraceContext {

    private final TraceParent traceParent = new TraceParent(this);
    private final TraceState traceState = new TraceState(this);
    private final TracePayload tracePayload = new TracePayload(this);

    public String getAccountId() {
        return "734056";
    }

    public String getApplicationId() {
        return "165402551";
    }

    public String getVendor() {
        return "@nr";
    }

    public String getSampled() {
        return "00";
    }

    public List<TraceHeader> getHeader() {
        return Arrays.asList(traceParent, traceState, tracePayload);
    }

    public TraceParent getTraceParent() {
        return traceParent;
    }

    public TraceState getTraceState() {
        return traceState;
    }

    public TracePayload getTracePayload() {
        return tracePayload;
    }
}
