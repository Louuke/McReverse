package org.jannsen.mcreverse.api.request.builder;

import org.jannsen.mcreverse.api.entity.akamai.SensorToken;
import org.jannsen.mcreverse.constants.Action;
import org.jannsen.mcreverse.utils.UserInfo;
import org.jannsen.mcreverse.utils.listener.ClientActionNotifier;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public class TokenProvider {

    private static final Map<UserInfo, Queue<SensorToken>> tokenCache = new ConcurrentHashMap<>();
    private final ClientActionNotifier clientAction;

    public TokenProvider(ClientActionNotifier clientAction) {
        this.clientAction = clientAction;
    }

    public SensorToken getSensorToken(UserInfo user) {
        tokenCache.putIfAbsent(user, new ConcurrentLinkedQueue<>());
        removeOldTokens(user);
        addToken(user);
        return tokenCache.get(user).poll();
    }

    private void addToken(UserInfo user) {
        if(tokenCache.get(user).isEmpty()) {
            SensorToken token = clientAction.notifyListener(Action.TOKEN_REQUIRED, SensorToken.class);
            Stream.generate(() -> token).limit(10).filter(Objects::nonNull).forEach(t -> tokenCache.get(user).add(t));
        }
    }

    private void removeOldTokens(UserInfo user) {
        tokenCache.get(user).removeIf(token -> Instant.now().getEpochSecond() - token.getCreatedTime() > 3600);
    }
}
