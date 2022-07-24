package icu.jnet.mcd.network;

import com.google.api.client.http.HttpRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestManager {

    private long last = 0;

    private static final Queue<HttpRequest> queue = new ConcurrentLinkedQueue<>();

    public void addRequest(HttpRequest request) {
        queue.add(request);
        while (!(last == 0 || (System.currentTimeMillis() - last > 330 && queue.peek() == request))) {
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
}
