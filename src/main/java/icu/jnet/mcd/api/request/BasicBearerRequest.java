package icu.jnet.mcd.api.request;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.UrlEncodedContent;
import icu.jnet.mcd.annotation.BasicAuth;

import java.util.HashMap;
import java.util.Map;

@BasicAuth
public class BasicBearerRequest implements Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/v1/security/auth/token";
    }

    @Override
    public HttpContent getContent() {
        Map<String, String> params = new HashMap<>();
        params.put("grantType", "client_credentials");
        return new UrlEncodedContent(params);
    }
}
