package org.jannsen.mcreverse.network;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.common.io.ByteStreams;
import org.jannsen.mcreverse.api.response.StreamResponse;
import org.jannsen.mcreverse.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

public class RequestScheduler {

    private final AutoRemoveQueue<Callable<HttpResponse>> queue = new AutoRemoveQueue<>(400);
    private final Base64.Encoder encoder = Base64.getEncoder();

    private static RequestScheduler instance;

    private RequestScheduler() {}

    public static RequestScheduler getInstance() {
        if(instance == null) {
            instance = new RequestScheduler();
        }
        return instance;
    }

    public Optional<StreamResponse> enqueueGetAsStream(HttpRequest request) {
        return enqueue(request::execute)
                .map(map -> new StreamResponse(request.getUrl().toString(),
                        encoder.encodeToString(((ByteArrayOutputStream) map.get("content")).toByteArray()),
                        ((long) map.get("length"))));
    }

    public Optional<String> enqueueGetString(HttpRequest request) {
        return enqueue(request::execute).map(map -> ((ByteArrayOutputStream) map.get("content"))
                .toString(StandardCharsets.UTF_8));
    }

    private Optional<Map<String, Object>> enqueue(Callable<HttpResponse> request) {
        while (queue.contains(request)) {
            Utils.waitMill(200);
        }
        Optional<HttpResponse> response = unchecked(request);
        ByteArrayOutputStream content = response.flatMap(r -> unchecked(r::getContent)).map(this::parseAsByteArray).orElse(new ByteArrayOutputStream());
        long contentLength = response.map(r -> r.getHeaders().getContentLength()).orElse(0L);
        return response.map(r -> Map.of("length", contentLength, "content", content));
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
