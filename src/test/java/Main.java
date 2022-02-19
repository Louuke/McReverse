import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import icu.jnet.mcd.api.McClient;

import java.io.IOException;

public class Main {

    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IOException {

        McClient client = new McClient();
        client.login("anselm@mailx.cyou", "123456aA!");
        System.out.println(gson.toJson(client.getOffers().getOffers()));
        System.out.println(gson.toJson(client.redeemCoupon("13372", "")));

        /*
        String url = "https://mcdonalds.fast-insight.com/voc/bs/api/v3/de/checkInvoice";
        //String body = "{\"country\": \"de\"}";
        String body = "{\"invoice\":\"b6t3-m8ix-jscu\",\"meta\":{\"env\":\"production\",\"country\":\"de\",\"lang\":\"de\",\"isFromApp\":false,\"userInformation\":{\"firstname\":\"\",\"lastname\":\"\",\"deviceId\":\"\",\"deviceToken\":\"\"}},\"csrf\":\"1fcaa94f-3ff5-41f7-9cde-615c698fe9be\"}";
        HttpContent content = ByteArrayContent.fromString("application/json", body);
        HttpRequest request = new NetHttpTransport().createRequestFactory().buildPostRequest(new GenericUrl(url), content);

        HttpHeaders headers = request.getHeaders();
        headers.setUserAgent("MCDSDK/15.0.29 (Android; 28; de-) GMA/7.5");
        headers.set("content-type", "application/json;");
        headers.set("cookie", "AWSALB=7KwJg8l7GVUTO9n8Twganc8LuaFeWwrdkCQZqdvjhNXj7Yp6DsytzWngNjKAml0ZBsm34Rv7fWqmveD7W3+CA3NorlPeW8MlGTAf6yF9c2wICLDId4bEjDbOfVrE;AWSALBCORS=7KwJg8l7GVUTO9n8Twganc8LuaFeWwrdkCQZqdvjhNXj7Yp6DsytzWngNjKAml0ZBsm34Rv7fWqmveD7W3+CA3NorlPeW8MlGTAf6yF9c2wICLDId4bEjDbOfVrE;csrf=1fcaa94f-3ff5-41f7-9cde-615c698fe9be");
        request.setHeaders(headers);
        System.out.println(request.execute().parseAsString());

         */
    }
}
