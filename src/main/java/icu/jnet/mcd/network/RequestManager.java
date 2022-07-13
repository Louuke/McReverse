package icu.jnet.mcd.network;

import com.google.api.client.http.HttpRequest;
import icu.jnet.mcd.model.ProxyModel;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestManager {

    private static final String host = "localhost";

    private final Queue<HttpRequest> queue = new ConcurrentLinkedQueue<>();

    public RequestManager() {
        new Thread(() -> {
            long last = 0;
            while(true) {
                long now = System.currentTimeMillis();
                if(now - last > 300) {
                    last = now;
                    queue.poll();
                }
                waitMill();
            }
        }).start();
    }

    public void addRequest(HttpRequest request) {
        queue.add(request);
        while (queue.contains(request)) {
            waitMill();
        }
    }

    private void waitMill() {
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
