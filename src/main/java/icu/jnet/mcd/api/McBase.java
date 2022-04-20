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
        request.setHeader("user-agent", "MCDSDK/15.0.44 (Android; 28; de-DE) GMA/7.6");
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
