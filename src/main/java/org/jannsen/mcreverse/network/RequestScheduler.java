package org.jannsen.mcreverse.network;

import com.google.api.client.http.HttpResponse;
import org.jannsen.mcreverse.utils.Utils;

import java.util.concurrent.*;

public class RequestScheduler {

    private final AutoRemoveQueue<Callable<HttpResponse>> queue = new AutoRemoveQueue<>(400);

    private static RequestScheduler instance;

    private RequestScheduler() {}

    public static RequestScheduler getInstance() {
        if(instance == null) {
            instance = new RequestScheduler();
        }
        return instance;
    }

    public HttpResponse enqueue(Callable<HttpResponse> request) throws Exception {
        queue.add(request);
        while (queue.contains(request)) {
            Utils.waitMill(200);
        }
        return request.call();
    }

    public void setRequestsPerSecond(double rps) {
        queue.setWait((long) (1000 / rps));
    }
}