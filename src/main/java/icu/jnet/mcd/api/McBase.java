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
        request.setHeader("mcd-uuid", "ab65a26f-b02c-416d-a5e5-a32df5ba762d"); // Can not be fully random?

        if(request.getURI().toString().endsWith("login")) {
            request.setHeader("mcd-marketid", "DE");
            request.setHeader("x-acf-sensor-data", "1,a,qbSwBwpqnKJ3beJkXuUoqjY3BDFbProCLiJF4T/7ihwLJuCCtx22bk2vETil2n8dXDbPWXEkK/eQSP5KcshyVzOIgdXCmc2UVnUxlK9ywDafYd++8IZpl+OOa3h9j2IrOSiJUP6ajWcj7qB3W4P7YkIRjhzcDJ/ZFCXy5TSlC3o=,NaoeIpVY1IpGD/aPZfDxey5QcU7TdaMq/GN7tWXBqvZ3gxVJrk3MW/60GQxVSsuy5VR43KeGeK6xOTomsqA7a+g7zMu2kPZkUMZfYQ8viTu5+YzGLf9vVOjNLAKdd7xCks7apAYQqB0uk2vBmCX9PuzxgYILysqgYF0jVgO6i/g=$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$1000,0,0");
        }
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
