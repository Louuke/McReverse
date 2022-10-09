package icu.jnet.mcd.network;

import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.utils.UserInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

public class RefreshManager {

    private final Map<UserInfo, Authorization> cachedAuth = new ConcurrentHashMap<>();
    private final StampedLock lock = new StampedLock();
    private long stamp;

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
        stamp = lock.writeLock();
    }

    public void unlock() {
        lock.unlockWrite(stamp);
    }

}
