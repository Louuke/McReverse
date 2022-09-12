package icu.jnet.mcd.network;

import com.google.api.client.http.HttpRequest;
import icu.jnet.mcd.utils.Utils;

import java.util.Objects;

public class RequestManager {

    private final AutoRemoveQueue<WrappedRequest> queue = new AutoRemoveQueue<>();

    private static RequestManager instance;

    public static RequestManager getInstance() {
        if(instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    public void enqueue(HttpRequest request) {
        WrappedRequest wrapped = new WrappedRequest(request);
        queue.add(wrapped);
        while (queue.contains(wrapped)) {
            Utils.waitMill(50);
        }
    }

    public void setRequestsPerSecond(double rps) {
        queue.setWait((long) (1000 / rps));
    }

    private static class WrappedRequest {

        private final int id;

        public WrappedRequest(HttpRequest request) {
            this.id = Objects.hash(System.currentTimeMillis(), request);
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof WrappedRequest req)) {
                return false;
            }
            return id == req.hashCode();
        }
    }
}
