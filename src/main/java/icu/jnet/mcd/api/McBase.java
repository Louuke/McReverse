package icu.jnet.mcd.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import icu.jnet.mcd.api.request.RefreshRequest;
import icu.jnet.mcd.api.request.Request;
import icu.jnet.mcd.model.Authorization;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.api.response.LoginResponse;
import icu.jnet.mcd.model.ProxyModel;
import icu.jnet.mcd.network.DelayHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

class McBase {

    private final HttpClientBuilder builder = HttpClientBuilder.create().disableCookieManagement();
    private final Gson gson = new Gson();
    private final Random rand = new Random();
    private ProxyModel proxy;
    final Authorization auth = new Authorization();
    String email;

    public McBase(ProxyModel proxy) {
        this.proxy = proxy;
        Credentials credentials = new UsernamePasswordCredentials(proxy.getUser(), proxy.getPassword());
        HttpHost proxyHost = new HttpHost(proxy.getHost(), proxy.getPort());
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), credentials);
        builder.setProxy(proxyHost).setDefaultCredentialsProvider(credsProvider);
    }

    public McBase() {}

    boolean success(Response response) {
        return response.getStatus().getType().equals("Success");
    }

    <T extends Response> T queryGet(Request request, Class<T> clazz)  {
        HttpGet httpRequest = new HttpGet(request.getUrl());
        return query(httpRequest, null, clazz);
    }

    <T extends Response> T queryPost(Request request, Class<T> clazz) {
        HttpEntity httpContent = request.getContent();
        HttpPost httpRequest = new HttpPost(request.getUrl());
        httpRequest.setEntity(httpContent);
        return query(httpRequest, httpContent.getContentType().getValue(), clazz);
    }

    <T extends Response> T queryDelete(Request request, Class<T> clazz) {
        HttpDelete httpRequest = new HttpDelete(request.getUrl());
        return query(httpRequest, null, clazz);
    }

    <T extends Response> T queryPut(Request request, Class<T> clazz) {
        HttpEntity httpContent = request.getContent();
        HttpPut httpRequest = new HttpPut(request.getUrl());
        httpRequest.setEntity(httpContent);
        return query(httpRequest, httpContent.getContentType().getValue(), clazz);
    }

    private <T extends Response> T query(HttpUriRequest request, String type, Class<T> clazz) {
        setRequestHeaders(request, type);
        try(DelayHttpClient client = new DelayHttpClient(builder.build(), proxy)) {
            HttpResponse response = client.execute(request);
            T cResponse = gson.fromJson(EntityUtils.toString(response.getEntity()), clazz);
            if(cResponse != null && cResponse.getStatus().getErrors().stream()
                    .anyMatch(error -> error.getErrorType().equals("JWTTokenExpired"))) { // Authorization expired
                if(loginRefresh()) {
                    return query(request, type, clazz);
                }
            } else {
                return cResponse;
            }
        } catch (JsonSyntaxException | IOException e) {
            System.out.println(e.getMessage());
        }
        return createInstance(clazz);
    }

    private boolean loginRefresh() {
        LoginResponse login = queryPost(new RefreshRequest(auth.getRefreshToken()), LoginResponse.class);
        if(success(login)) {
            auth.updateAccessToken(login.getAccessToken());
            auth.updateRefreshToken(login.getRefreshToken());
            return true;
        }
        return false;
    }

    private void setRequestHeaders(HttpUriRequest request, String type) {
        String token = !auth.getAccessToken().isEmpty()
                ? auth.getAccessToken()
                : "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";

        request.setHeader("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        request.setHeader("authorization", token);
        request.setHeader("accept-charset", "UTF-8");
        request.setHeader("content-type", type != null ? type.replace("charset=UTF-8", "") : "application/json;");
        request.setHeader("accept-language", "de-DE");
        request.setHeader("user-agent", "MCDSDK/19.0.60 (Android; 30; de-DE) GMA/7.7");
        request.setHeader("mcd-sourceapp", "GMA");
        request.setHeader("mcd-marketid", "DE");
        request.setHeader("x-acf-sensor-data", "1,a,jmEUsdyC4opPebclMFNOR8KUGtcPn1MWBNWoLmicY7IxTcS2SVxISA/q4FbowjvpTYIPW2kcNQ+uen/7D/WBDuwI6/P69WGUAHQEuSsR+UjvZ97me5AcYE3q9hS+p3YogiGKWbjH2rpYxYgx9AXXh+zueJ9z8g+IeJqlFRpxtSY=,BmXtzZr3te3Pcknw+iAjCX7H8vFptgeksUZ0W4ZBOc0QYyT8WC+aw1+p4x+UA5trN9EPe2yXbaLfve4wmvC8PCa+Wc0gTEGN6eWIy5qrAQw1YeHbNqgY55tQ2+4x9S5u5TXINs6+L86urayxGCu3FLMitVNNHAHa4QfPz+IQS9c=$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$0,1000,0");
        request.setHeader("mcd-uuid", "ab65a26f-b02c-416d-a5e5-a32df5ba762d"); // Can not be fully random?
    }

    private <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
