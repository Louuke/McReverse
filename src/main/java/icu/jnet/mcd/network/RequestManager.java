package icu.jnet.mcd.network;

import com.google.api.client.http.HttpRequest;
import icu.jnet.mcd.model.ProxyModel;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestManager {

    private static final String host = "localhost";

    private static final Map<String, Queue<HttpRequest>> restMap = new ConcurrentHashMap<>();
    private static final Map<String, Long> timeMap = new ConcurrentHashMap<>();

    public static void addRequest(HttpRequest request) {
        if(!restMap.containsKey(host)) {
            Queue<HttpRequest> queue = new ConcurrentLinkedQueue<>();
            queue.add(request);
            restMap.put(host, queue);
        } else {
            restMap.get(host).add(request);
        }

        while (timeMap.containsKey(host) && (System.currentTimeMillis() - timeMap.get(host) <= 100
                || restMap.get(host).peek() != request)) {
            waitMill();
        }
    }

    public static void removeLast() {
        timeMap.put(host, System.currentTimeMillis());
        restMap.get(host).poll();
    }

    private static void waitMill() {
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
