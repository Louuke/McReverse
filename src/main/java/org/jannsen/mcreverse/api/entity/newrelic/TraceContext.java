package org.jannsen.mcreverse.api.entity.newrelic;

import java.util.Arrays;
import java.util.List;

public class TraceContext {

    private final TraceParent traceParent = new TraceParent(this);
    private final TraceState traceState = new TraceState(this);
    private final TracePayload tracePayload = new TracePayload(this);
    private final TraceNewRelicID traceNewRelicID = new TraceNewRelicID();

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
        return Arrays.asList(traceParent, traceState, tracePayload, traceNewRelicID);
    }

    public TraceParent getTraceParent() {
        return traceParent;
    }

    public TraceState getTraceState() {
        return traceState;
    }
}
