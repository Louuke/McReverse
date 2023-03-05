package org.jannsen.mcreverse.api.request.builder;

import org.jannsen.mcreverse.api.entity.akamai.SensorToken;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TokenProvider {

    private static final Map<String, Queue<SensorToken>> tokenCache = new ConcurrentHashMap<>();
    private Supplier<SensorToken> tokenSupplier;

    public void setTokenSupplier(Supplier<SensorToken> tokenSupplier) {
        this.tokenSupplier = tokenSupplier;
    }

    public SensorToken getSensorToken(String email) {
        tokenCache.putIfAbsent(email, new ConcurrentLinkedQueue<>());
        removeOldTokens(email);
        addToken(email);
        return tokenCache.get(email).poll();
    }

    private void addToken(String email) {
        if(tokenCache.get(email).isEmpty()) {
            SensorToken token = tokenSupplier != null ? tokenSupplier.get() : new SensorToken();
            for(int i = 0; i < (token.getToken() != null ? 3 : 1); i++) {
                tokenCache.get(email).add(token);
            }
        }
    }

    private void removeOldTokens(String email) {
        tokenCache.get(email).removeIf(token -> Instant.now().getEpochSecond() - token.getCreatedTime() > 3600);
    }
}
