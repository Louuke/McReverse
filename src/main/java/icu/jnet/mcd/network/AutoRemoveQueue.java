package icu.jnet.mcd.network;

import icu.jnet.mcd.helper.Utils;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoRemoveQueue<E> extends ConcurrentLinkedQueue<E> {

    private Thread thread;
    private long wait;

    public AutoRemoveQueue() {
        this.wait = 200;
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
                    Utils.waitMill(wait);
                    poll();
                }
            });
            thread.start();
        }
    }

    public void setWait(long wait) {
        this.wait = wait;
    }
}
