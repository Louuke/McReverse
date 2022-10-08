package icu.jnet.mcd.network;

import icu.jnet.mcd.utils.UserInfo;
import icu.jnet.mcd.utils.Utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RefreshManager {

    private final Queue<UserInfo> queue = new ConcurrentLinkedQueue<>();

    private static RefreshManager instance;

    private RefreshManager() {}

    public static RefreshManager getInstance() {
        if(instance == null) {
            instance = new RefreshManager();
        }
        return instance;
    }

    public synchronized boolean isWaitingForLock(UserInfo user) {
        return queue.contains(user);
    }

    public void waitForLock(UserInfo user) {
        queue.add(user);
        for(int i = 0; i < 60000 && queue.peek() != user; i += 300) {
            Utils.waitMill(300);
        }
    }

    public void unlock(UserInfo user) {
        queue.remove(user);
    }

    public void waitForUnlock(UserInfo user) {
        for(int i = 0; i < 60000 && queue.contains(user); i += 300) {
            Utils.waitMill(300);
        }
    }
}
