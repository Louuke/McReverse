package org.jannsen.mcreverse.network;

import org.jannsen.mcreverse.api.entity.login.Authorization;
import org.jannsen.mcreverse.utils.UserInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class RefreshManager {

    private final Map<UserInfo, Authorization> cachedAuth = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    private static RefreshManager instance;

    private RefreshManager() {}

    public static RefreshManager getInstance() {
        if(instance == null) {
            instance = new RefreshManager();
        }
        return instance;
    }

    public boolean isNewerAuthCached(UserInfo user, Authorization authorization) {
        return cachedAuth.containsKey(user) && cachedAuth.get(user).getCreatedUnix() > authorization.getCreatedUnix();
    }

    public Authorization getCachedAuthorization(UserInfo user) {
        return cachedAuth.get(user);
    }

    public void saveAuthorization(UserInfo user, Authorization authorization) {
        cachedAuth.put(user, authorization);
    }

    public void waitForLock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

}
