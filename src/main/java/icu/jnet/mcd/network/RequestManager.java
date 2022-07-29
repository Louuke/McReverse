package icu.jnet.mcd.network;

import com.google.api.client.http.HttpRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestManager {

    private final Queue<HttpRequest> queue = new ConcurrentLinkedQueue<>();
    private static RequestManager instance;

    private RequestManager() {
        startPolling();
    }

    public static RequestManager getInstance() {
        if(instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    public void addRequest(HttpRequest request) {
        queue.add(request);
        while (queue.contains(request)) {
            waitMill(100);
        }
    }

    private void startPolling() {
        new Thread(() -> {
            while(true) {
                waitMill(400);
                queue.poll();
            }
        }).start();
    }

    private void waitMill(long mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
