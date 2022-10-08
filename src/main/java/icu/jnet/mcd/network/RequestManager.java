package icu.jnet.mcd.network;

import com.google.api.client.http.HttpRequest;
import icu.jnet.mcd.utils.Utils;

public class RequestManager {

    private final AutoRemoveQueue<HttpRequest> queue = new AutoRemoveQueue<>(400);

    private static RequestManager instance;

    private RequestManager() {}

    public static RequestManager getInstance() {
        if(instance == null) {
            instance = new RequestManager();
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
