package icu.jnet.mcd.api;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import icu.jnet.mcd.api.request.LoginRequest;
import icu.jnet.mcd.api.request.RefreshRequest;
import icu.jnet.mcd.auth.Authorization;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.api.response.AuthResponse;
import icu.jnet.mcd.api.response.LoginResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

class MacBase {

    final String API_ENDPOINT = "https://eu-prod.api.mcd.com";
    final Authorization auth = new Authorization();
    final Gson gson = new Gson();

    public MacBase() {
        getAccessToken();
    }

    private void getAccessToken() {
        String url1 = "/v1/security/auth/token";
        HashMap<String, String> params = new HashMap<>();
        params.put("grantType", "client_credentials");
        AuthResponse authResponse = gson.fromJson(queryPost(url1, new UrlEncodedContent(params)), AuthResponse.class);
        auth.updateAccessToken(authResponse.getToken());
    }

    private boolean loginRefresh() {
        String url = "/exp/v1/customer/login/refresh";
        String body = gson.toJson(new RefreshRequest(auth.getRefreshToken()));
        LoginResponse login = gson.fromJson(queryPost(url, ByteArrayContent.fromString("application/json", body)), LoginResponse.class);
        if(login.getStatus().getType().equals("Success")) {
            auth.updateAccessToken(login.getAccessToken());
            auth.updateRefreshToken(login.getRefreshToken());
            return true;
        }
        return false;
    }

    String queryGet(String url)  {
        try {
            url = API_ENDPOINT + url;
            HttpRequest request = new NetHttpTransport().createRequestFactory().buildGetRequest(new GenericUrl(url));
            setRequestHeaders(request);
            return request.execute().parseAsString();
        } catch (HttpResponseException e) {
            Response response = gson.fromJson(e.getContent(), Response.class);
            if(response.getStatus().getType().equals("ValidationException")) { // Authorization expired
                if(loginRefresh() && !auth.getRefreshToken().isEmpty()) {
                    return queryGet(url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\": {\"code\": -1,\"type\": \"Error\"}}";
    }

    String queryPost(String url, HttpContent httpContent) {
        try {
            url = API_ENDPOINT + url;
            HttpRequest request = new NetHttpTransport().createRequestFactory().buildPostRequest(new GenericUrl(url), httpContent);
            setRequestHeaders(request);
            return request.execute().parseAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\": {\"code\": -1,\"type\": \"Error\"}}";
    }

    String queryPut(String url, HttpContent httpContent) {
        try {
            url = API_ENDPOINT + url;
            HttpRequest request = new NetHttpTransport().createRequestFactory().buildPutRequest(new GenericUrl(url), httpContent);
            setRequestHeaders(request);
            return request.execute().parseAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\": {\"code\": -1,\"type\": \"Error\"}}";
    }

    private void setRequestHeaders(HttpRequest request) {
        String token = !auth.getAccessToken().isEmpty()
                ? auth.getAccessToken()
                : "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";

        HttpHeaders headers = new HttpHeaders();
        headers.set("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        headers.set("authorization", token);
        headers.set("accept-charset", "UTF-8");
        headers.set("content-type", "application/json; charset=UTF-8");
        headers.set("accept-language", "de-DE");
        headers.setUserAgent("MCDSDK/15.0.29 (Android; 28; de-) GMA/7.5");
        headers.set("mcd-sourceapp", "GMA");
        headers.set("mcd-uuid", "ed088d2c-e5df-4cbe-92e9-702ca00ddc4b");
        headers.set("mcd-marketid", "DE");
        request.setHeaders(headers);
    }
}
