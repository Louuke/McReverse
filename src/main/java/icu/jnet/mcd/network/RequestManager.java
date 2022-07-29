package icu.jnet.mcd.network;

import com.google.api.client.http.HttpRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestManager {

    private static Queue<HttpRequest> queue;

    public static void addRequest(HttpRequest request) {
        initQueue();

        queue.add(request);
        while (queue.contains(request)) {
            waitMill(100);
        }
    }

    private static void initQueue() {
        if(queue == null) {
            queue = new ConcurrentLinkedQueue<>();
            startPolling();
        }
    }

    private static void startPolling() {
        new Thread(() -> {
            waitMill(400);
            queue.poll();
        }).start();
    }

    private static void waitMill(long mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
