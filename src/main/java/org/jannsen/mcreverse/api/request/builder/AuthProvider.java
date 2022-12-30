package org.jannsen.mcreverse.api.request.builder;

import org.jannsen.mcreverse.api.entity.auth.Authorization;
import org.jannsen.mcreverse.api.entity.auth.BasicAuthorization;
import org.jannsen.mcreverse.api.entity.auth.BasicBearerAuthorization;
import org.jannsen.mcreverse.api.entity.auth.BearerAuthorization;
import org.jannsen.mcreverse.api.request.Request;
import org.jannsen.mcreverse.utils.UserInfo;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class AuthProvider {

    private final Supplier<BasicBearerAuthorization> basicBearerSupplier;
    private final Supplier<BearerAuthorization> bearerSupplier;

    public AuthProvider(Supplier<BasicBearerAuthorization> basicBearerSupplier,
                        Supplier<BearerAuthorization> bearerSupplier) {
        this.basicBearerSupplier = basicBearerSupplier;
        this.bearerSupplier = bearerSupplier;
    }

    public Authorization getAppropriateAuth(Request request) {
        return switch (request.getAuthType()) {
            case Basic -> new BasicAuthorization();
            case BasicBearer -> basicBearerSupplier.get();
            case Bearer -> bearerSupplier.get();
        };
    }
}
