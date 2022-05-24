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

        request.setHeader("host", "eu-prod.api.mcd.com");
        request.setHeader("x-acf-sensor-data", "1,a,Y30kQPpiUGvz9F+Etvm3OR07pwTICsmFOSHoka0JNi17Ia3fTSuqWvLDb8plKTUCg5GExOEA0NOSi+lnRGZzM9UO76XkckuHG8D2R75/3Uh92X/REH3cCvj/fxQVXZKqKs4ZCwHbjIut7AZ3NM/YIEO0DVXFcGtMwSbDG/LGbm8=,W5GMXtkz9kRHbTpZ+B3z8cqKG+jAnAP/8/ECb69d62pTqFvADcRS6rbu6kWGvGPYYuib98KhDUQ7uK/gpr4pNAyDDs6bYy3NFDGxK1Tbhbb88ZfK6ckSbRJ1rSLGzWzGC26aKduxzrKaTOzk3SjV2vtTJS8GoX95Z78dwRyxOGM=$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$1000,0,0");
        request.setHeader("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        request.setHeader("authorization", token);
        request.setHeader("accept-charset", "UTF-8");
        request.setHeader("content-type", type != null ? type.replace("charset=UTF-8", "") : "application/json;");
        request.setHeader("accept-language", "de-DE");
        request.setHeader("user-agent", "MCDSDK/19.0.57 (Android; 30; de-DE) GMA/7.7");
        request.setHeader("mcd-sourceapp", "GMA");
        request.setHeader("mcd-uuid", (rand.nextInt(90000) + 10000) + "c4d-e5df-4cbe-92e9-702ca00ddc4c"); // Can not be fully random?
        request.setHeader("mcd-marketid", "DE");
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
