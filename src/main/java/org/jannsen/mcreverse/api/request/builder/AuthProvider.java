package org.jannsen.mcreverse.api.request.builder;

import org.jannsen.mcreverse.api.entity.auth.Authorization;
import org.jannsen.mcreverse.api.entity.auth.BasicAuthorization;
import org.jannsen.mcreverse.api.entity.auth.BasicBearerAuthorization;
import org.jannsen.mcreverse.api.entity.auth.BearerAuthorization;
import org.jannsen.mcreverse.api.request.Request;
import org.jannsen.mcreverse.utils.UserInfo;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class AuthProvider {

    private static final Map<UserInfo, BearerAuthorization> cachedAuth = new ConcurrentHashMap<>();
    private static final ReentrantLock lock = new ReentrantLock();

    private final Supplier<BasicBearerAuthorization> basicBearerSupplier;
    private final Supplier<BearerAuthorization> bearerSupplier;
    private final UserInfo user;

    public AuthProvider(UserInfo user, Supplier<BearerAuthorization> bearerSupplier,
                        Supplier<BasicBearerAuthorization> basicBearerSupplier) {
        this.basicBearerSupplier = basicBearerSupplier;
        this.bearerSupplier = bearerSupplier;
        this.user = user;
    }

    public Authorization getAppropriateAuth(Request request) {
        return switch (request.getAuthType()) {
            case Basic -> new BasicAuthorization();
            case BasicBearer -> basicBearerSupplier.get();
            case Bearer -> bearerSupplier.get();
        };
    }

    public BearerAuthorization scheduleRefresh(Callable<BearerAuthorization> refresh) {
        lock.lock();
        BearerAuthorization authorization = bearerSupplier.get();
        try {
            if(isNewerAuthCached(user, authorization)) {
                authorization = cachedAuth.get(user);
            } else {
                authorization = refresh.call();
                saveAuthorization(user, authorization);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return authorization;
    }

    private boolean isNewerAuthCached(UserInfo user, BearerAuthorization authorization) {
        return cachedAuth.containsKey(user) && cachedAuth.get(user).getCreatedUnix() > authorization.getCreatedUnix();
    }

    private void saveAuthorization(UserInfo user, BearerAuthorization authorization) {
        cachedAuth.put(user, authorization);
    }
}
