package org.jannsen.mcreverse.api.entity.newrelic;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TracePayload implements TraceHeader {

    private final int[] v = {0, 2};
    private final Map<String, Object> d = new HashMap<>();

    public TracePayload(TraceContext context) {
        d.put("d.ty", "Mobile");
        d.put("d.ac", context.getAccountId());
        d.put("d.ap", context.getApplicationId());
        d.put("d.tr", context.getTraceState().getTraceId());
        d.put("d.id", context.getTraceParent().getParentId());
        d.put("d.ti", System.currentTimeMillis());
    }

    @Override
    public String getHeaderName() {
        return "newrelic";
    }

    @Override
    public String getHeaderValue() {
        String json = new Gson().toJson(this);
        return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }
}
