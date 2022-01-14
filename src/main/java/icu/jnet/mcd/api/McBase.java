package icu.jnet.mcd.api;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import icu.jnet.mcd.api.request.AccessRequest;
import icu.jnet.mcd.api.request.RefreshRequest;
import icu.jnet.mcd.api.request.Request;
import icu.jnet.mcd.api.response.status.Status;
import icu.jnet.mcd.auth.Authorization;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.api.response.AuthResponse;
import icu.jnet.mcd.api.response.LoginResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

class McBase {

    final Authorization auth = new Authorization();
    final Gson gson = new Gson();

    public McBase() {
        getAccessToken();
    }

    private void getAccessToken() {
        Request request = new AccessRequest();
        AuthResponse authResponse = queryPost(request, AuthResponse.class);
        auth.updateAccessToken(authResponse.getToken());
    }

    private boolean loginRefresh() {
        Request request = new RefreshRequest(auth.getRefreshToken());
        LoginResponse login = queryPost(request, LoginResponse.class);
        if(login.getStatus().getType().equals("Success")) {
            auth.updateAccessToken(login.getAccessToken());
            auth.updateRefreshToken(login.getRefreshToken());
            return true;
        }
        return false;
    }

    <T extends Response> T queryGet(Request request, Class<T> clazz)  {
        try {
            String url = request.getUrl();
            HttpRequest httpRequest = new NetHttpTransport().createRequestFactory().buildGetRequest(new GenericUrl(url));
            return query(httpRequest, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    <T extends Response> T queryPost(Request request, Class<T> clazz) {
        try {
            String url = request.getUrl();
            HttpContent httpContent = request.getContent();
            HttpRequest httpRequest = new NetHttpTransport().createRequestFactory().buildPostRequest(new GenericUrl(url), httpContent);
            return query(httpRequest, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    <T extends Response> T queryPut(Request request, Class<T> clazz) {
        try {
            String url = request.getUrl();
            HttpContent httpContent = request.getContent();
            HttpRequest httpRequest = new NetHttpTransport().createRequestFactory().buildPutRequest(new GenericUrl(url), httpContent);
            return query(httpRequest, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    private <T extends Response> T query(HttpRequest request, Class<T> clazz) {
        try {
            setRequestHeaders(request);
            return gson.fromJson(request.execute().parseAsString(), clazz);
        } catch (HttpResponseException e) {
            try {
                Response response = gson.fromJson(e.getContent(), Response.class);
                if(response.getStatus().getErrors().size() > 0
                        && response.getStatus().getErrors().get(0).getErrorType().equals("JWTTokenExpired")) { // Authorization expired
                    if(loginRefresh() && !auth.getRefreshToken().isEmpty()) {
                        return query(request, clazz);
                    }
                } else {
                    System.out.println(e.getContent());
                }
            } catch (JsonSyntaxException e2) {
                e2.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    private void setRequestHeaders(HttpRequest request) {
        String token = !auth.getAccessToken().isEmpty()
                ? auth.getAccessToken()
                : "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";

        HttpHeaders headers = new HttpHeaders();
        headers.set("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        headers.set("authorization", token);
        headers.set("accept-charset", "UTF-8");
        headers.set("content-type", request.getContent() != null ? request.getContent().getType() : "application/json;");
        headers.set("accept-language", "de-DE");
        headers.setUserAgent("MCDSDK/15.0.29 (Android; 28; de-) GMA/7.5");
        headers.set("mcd-sourceapp", "GMA");
        headers.set("mcd-uuid", "ed088d2c-e5df-4cbe-92e9-702ca00ddc4b");
        headers.set("mcd-marketid", "DE");
        request.setHeaders(headers);
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
