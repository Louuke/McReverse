package org.jannsen.mcreverse.network;

import com.google.api.client.http.HttpResponse;
import com.google.common.io.ByteStreams;
import org.jannsen.mcreverse.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    public Optional<String> enqueueGetAsBase64(Callable<HttpResponse> request) {
        return enqueue(request).map(ByteArrayOutputStream::toByteArray)
                .map(bytes -> Base64.getEncoder().encodeToString(bytes));
    }

    public Optional<String> enqueueGetString(Callable<HttpResponse> request) {
        return enqueue(request).map(stream -> stream.toString(StandardCharsets.UTF_8));
    }

    private Optional<ByteArrayOutputStream> enqueue(Callable<HttpResponse> request) {
        while (queue.contains(request)) {
            Utils.waitMill(200);
        }
        return unchecked(() -> request.call().getContent()).map(this::parseAsByteArray);
    }

    private ByteArrayOutputStream parseAsByteArray(InputStream stream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        unchecked(() -> copyStream(stream, out));
        return out;
    }

    private long copyStream(InputStream in, OutputStream out) throws IOException {
        long bytes = ByteStreams.copy(in, out);
        in.close();
        return bytes;
    }

    private <V> Optional<V> unchecked(Callable<V> callable) {
        try {
            return Optional.ofNullable(callable.call());
        } catch (SocketTimeoutException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void setRequestsPerSecond(double rps) {
        queue.setWait((long) (1000 / rps));
    }
}
