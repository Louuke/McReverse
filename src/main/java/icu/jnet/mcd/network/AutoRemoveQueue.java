package icu.jnet.mcd.network;

import icu.jnet.mcd.utils.Utils;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoRemoveQueue<E> extends ConcurrentLinkedQueue<E> {

    private Thread thread;
    private long waitMillis;

    public AutoRemoveQueue(int waitMillis) {
        this.waitMillis = waitMillis;
    }

    @Override
    public boolean add(E e) {
        boolean bool = super.add(e);
        executeAutoRemove();
        return bool;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean bool = super.addAll(c);
        executeAutoRemove();
        return bool;
    }

    private void executeAutoRemove() {
        if(thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                while(!isEmpty()) {
                    Utils.waitMill(waitMillis);
                    poll();
                }
            });
            thread.start();
        }
    }

    public void setWait(long waitMillis) {
        this.waitMillis = waitMillis;
    }
}
