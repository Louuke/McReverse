package org.jannsen.mcreverse.network;

import com.google.api.client.http.HttpRequest;
import org.jannsen.mcreverse.utils.Utils;

public class RequestScheduler {

    private final AutoRemoveQueue<HttpRequest> queue = new AutoRemoveQueue<>(400);

    private static RequestScheduler instance;

    private RequestScheduler() {}

    public static RequestScheduler getInstance() {
        if(instance == null) {
            instance = new RequestScheduler();
        }
        return instance;
    }

    public void enqueue(HttpRequest request) {
        queue.add(request);
        while (queue.contains(request)) {
            Utils.waitMill(200);
        }
    }

    public void setRequestsPerSecond(double rps) {
        queue.setWait((long) (1000 / rps));
    }
}
