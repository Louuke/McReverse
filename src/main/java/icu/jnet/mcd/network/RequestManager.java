package icu.jnet.mcd.network;

import icu.jnet.mcd.model.ProxyModel;
import org.apache.http.HttpRequest;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

class RequestManager {

    private static final Map<String, Queue<HttpRequest>> restMap = new ConcurrentHashMap<>();
    private static final Map<String, Long> timeMap = new ConcurrentHashMap<>();

    static void addAndWait(HttpRequest request, ProxyModel proxy) {
        String host = getHost(proxy);
        if(!restMap.containsKey(host)) {
            Queue<HttpRequest> queue = new ConcurrentLinkedQueue<>();
            queue.add(request);
            restMap.put(host, queue);
        } else {
            restMap.get(host).add(request);
        }

        while(timeMap.containsKey(host) && System.currentTimeMillis() - timeMap.get(host) <= 225
            && restMap.get(host).peek() == request) {
            waitMill();
        }
    }

    static void removeRequest(HttpRequest request, ProxyModel proxy) {
        String host = getHost(proxy);
        timeMap.put(host, System.currentTimeMillis());
        restMap.get(host).remove(request);
    }

    private static String getHost(ProxyModel proxy) {
        return proxy != null ? proxy.getHost() : "localhost";
    }

    private static void waitMill() {
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
