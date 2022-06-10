package icu.jnet.mcd.network;

import icu.jnet.mcd.model.ProxyModel;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import static icu.jnet.mcd.network.RequestManager.*;

import java.io.Closeable;
import java.io.IOException;

public class DelayHttpClient implements Closeable {

    private final CloseableHttpClient client;
    private final ProxyModel proxy;

    public DelayHttpClient(CloseableHttpClient client, ProxyModel proxy) {
        this.client = client;
        this.proxy = proxy;
    }

    public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
        addRequest(request, proxy);
        CloseableHttpResponse response = client.execute(request);
        removeLast(proxy);
        return response;
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}
