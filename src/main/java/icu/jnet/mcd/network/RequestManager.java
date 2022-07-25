package icu.jnet.mcd.network;

import com.google.api.client.http.HttpRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestManager {

    private long last = 0;

    private transient Queue<HttpRequest> queue;

    public void addRequest(HttpRequest request) {
        initQueue();

        queue.add(request);
        while (!(last == 0 || (System.currentTimeMillis() - last > 300 && queue.peek() == request))) {
            waitMill();
        }
        last = System.currentTimeMillis();
        queue.poll();
    }

    private void waitMill() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initQueue() {
        if(queue == null) {
            queue = new ConcurrentLinkedQueue<>();
        }
    }
}
