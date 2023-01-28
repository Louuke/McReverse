package org.jannsen.mcreverse.network;

import com.google.api.client.http.HttpResponse;
import org.jannsen.mcreverse.utils.Utils;

import java.util.Optional;
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

    public Optional<String> enqueue(Callable<HttpResponse> request) {
        try {
            while (queue.contains(request)) {
                Utils.waitMill(200);
            }
            String responseContent = request.call().parseAsString();
            if(!responseContent.isEmpty()) {
                return Optional.of(responseContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void setRequestsPerSecond(double rps) {
        queue.setWait((long) (1000 / rps));
    }
}
