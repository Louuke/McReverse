package org.jannsen.mcreverse.network;

import com.google.api.client.http.HttpResponse;
import com.google.common.io.ByteStreams;
import org.jannsen.mcreverse.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    public Optional<String> enqueueString(Callable<HttpResponse> request) {
        return enqueueStream(request).map(stream -> stream.toString(StandardCharsets.UTF_8));
    }

    public Optional<ByteArrayOutputStream> enqueueStream(Callable<HttpResponse> request) {
        try {
            while (queue.contains(request)) {
                Utils.waitMill(200);
            }
            return Optional.ofNullable(request.call().getContent()).map(this::parseAsByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private ByteArrayOutputStream parseAsByteArray(InputStream stream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ByteStreams.copy(stream, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void setRequestsPerSecond(double rps) {
        queue.setWait((long) (1000 / rps));
    }
}
