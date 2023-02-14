package org.jannsen.mcreverse;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jannsen.mcreverse.api.entity.akamai.SensorToken;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

public class SensorFetcher implements Supplier<SensorToken> {

    @Override
    public SensorToken get() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.mccoupon.deals/token"))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            JsonElement element = JsonParser.parseString(body);
            String t = element.getAsJsonObject().get("response").getAsJsonObject().get("token").getAsString();
            long l = element.getAsJsonObject().get("response").getAsJsonObject().get("createdTime").getAsLong();
            return new SensorToken(t, l);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
