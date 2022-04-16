package icu.jnet.mcd.api.request;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AccessRequest implements Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/v1/security/auth/token";
    }

    @Override
    public HttpEntity getContent() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grantType", "client_credentials"));
        return new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
    }
}
