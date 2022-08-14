package icu.jnet.mcd.api;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import icu.jnet.mcd.api.request.RefreshRequest;
import icu.jnet.mcd.api.request.Request;
import icu.jnet.mcd.api.response.status.Status;
import icu.jnet.mcd.model.Authorization;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.api.response.LoginResponse;
import icu.jnet.mcd.model.UserInfo;
import icu.jnet.mcd.model.listener.ClientStateListener;
import icu.jnet.mcd.network.RequestManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class McBase {

    private static final HttpRequestFactory factory = new NetHttpTransport().createRequestFactory();
    private static final RequestManager reqManager = RequestManager.getInstance();
    private static final Gson gson = new Gson();
    private final transient List<ClientStateListener> stateListener = new ArrayList<>();
    private final Authorization authorization = new Authorization();
    private final UserInfo userInfo = new UserInfo();

    <T extends Response> T queryGet(Request request, Class<T> clazz)  {
        try {
            String url = request.getUrl();
            HttpRequest httpRequest = factory.buildGetRequest(new GenericUrl(url));
            return query(httpRequest, clazz, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    <T extends Response> T queryPost(Request request, Class<T> clazz) {
        try {
            String url = request.getUrl();
            HttpContent httpContent = request.getContent();
            HttpRequest httpRequest = factory.buildPostRequest(new GenericUrl(url), httpContent);
            return query(httpRequest, clazz, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    <T extends Response> T queryDelete(Request request, Class<T> clazz) {
        try {
            String url = request.getUrl();
            HttpRequest httpRequest = factory.buildDeleteRequest(new GenericUrl(url));
            return query(httpRequest, clazz, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    <T extends Response> T queryPut(Request request, Class<T> clazz) {
        try {
            String url = request.getUrl();
            HttpContent httpContent = request.getContent();
            HttpRequest httpRequest = factory.buildPutRequest(new GenericUrl(url), httpContent);
            return query(httpRequest, clazz, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    private <T extends Response> T query(HttpRequest request, Class<T> clazz, Request mcdRequest) {
        try {
            request.setReadTimeout(8000);
            setRequestHeaders(request, mcdRequest);
            reqManager.enqueue(request);
            return gson.fromJson(request.execute().parseAsString(), clazz);
        } catch (HttpResponseException e) {
            Response errorResponse = createErrorResponse(e);
            if(errorResponse != null) {
                return handleHttpError(errorResponse, request, clazz, mcdRequest);
            }
        } catch (IOException ignored) {}
        return createInstance(clazz);
    }

    private <T extends Response> T handleHttpError(Response errorResponse, HttpRequest request, Class<T> clazz, Request mcdRequest) {
        if(errorResponse.getStatus().getErrors().stream()
                .anyMatch(error -> error.getErrorType().equals("JWTTokenExpired"))) { // Authorization expired
            if(loginRefresh()) {
                return query(request, clazz, mcdRequest);
            }
            notifyExpirationListeners();
        }
        return createInstance(clazz, errorResponse.getStatus());
    }

    private Response createErrorResponse(HttpResponseException exception) {
        try {
            return gson.fromJson(exception.getContent(), Response.class);
        } catch (JsonSyntaxException ignored) {
            System.err.println(exception.getContent());
        }
        return null;
    }

    private boolean loginRefresh() {
        LoginResponse login = queryPost(new RefreshRequest(authorization.getRefreshToken()), LoginResponse.class);
        if(success(login)) {
            authorization.updateRefreshToken(login.getRefreshToken());
            authorization.updateAccessToken(login.getAccessToken(), true);
            return true;
        }
        return false;
    }

    private void setRequestHeaders(HttpRequest request, Request mcdRequest) {
        String token = !authorization.getAccessToken().isEmpty()
                ? authorization.getAccessToken()
                : "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";

        HttpHeaders headers = new HttpHeaders();
        headers.set("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        headers.set("authorization", token);
        headers.set("accept-charset", "UTF-8");
        headers.set("content-type", request.getContent() != null ? request.getContent().getType() : "application/json;");
        headers.set("accept-language", "de-DE");
        headers.set("user-agent", "MCDSDK/22.0.20 (Android; 30; de-DE) GMA/7.8");
        headers.set("mcd-sourceapp", "GMA");
        headers.set("mcd-uuid", UUID.randomUUID());

        if(mcdRequest.isSensorRequired()) {
            headers.set("mcd-marketid", "DE");
            headers.set("x-acf-sensor-data", getSensorToken());
        }
        request.setHeaders(headers);
    }

    private <T extends Response> T createInstance(Class<T> clazz) {
        return createInstance(clazz, new Status());
    }

    private <T extends Response> T createInstance(Class<T> clazz, Status status) {
        try {
            return clazz.getConstructor(Status.class).newInstance(status);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addStateListener(ClientStateListener listener) {
        stateListener.add(listener);
        authorization.addStateListener(listener);
    }

    boolean success(Response response) {
        return response.getStatus().getType().equals("Success");
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getEmail() {
        return getUserInfo().getEmail();
    }

    public void setRequestsPerSecond(double rps) {
        reqManager.setRequestsPerSecond(rps);
    }

    private String getSensorToken() {
        return stateListener.stream().map(ClientStateListener::tokenRequired)
                .filter(Objects::nonNull)
                .findAny().orElse("");
    }

    private void notifyExpirationListeners() {
        for(ClientStateListener listener : stateListener) {
            listener.loginExpired();
        }
    }
}
