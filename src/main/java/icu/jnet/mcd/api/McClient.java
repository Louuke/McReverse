package icu.jnet.mcd.api;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import icu.jnet.mcd.api.request.*;
import icu.jnet.mcd.api.response.*;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class McClient extends McBase {

    public static final String DEFAULT_DEVICE_ID = "75408e58622a88c6";

    public boolean login(@Nonnull String email, @Nonnull String password) {
        return login(email, password, DEFAULT_DEVICE_ID);
    }

    public boolean login(@Nonnull String email, @Nonnull String password, @Nonnull String deviceId) {
        String url2 = "https://eu-prod.api.mcd.com/exp/v1/customer/login";
        String body = gson.toJson(new LoginRequest(email, password, deviceId));
        LoginResponse login = gson.fromJson(queryPost(url2, ByteArrayContent.fromString("application/json", body)), LoginResponse.class);
        auth.updateAccessToken(login.getAccessToken());
        auth.updateRefreshToken(login.getRefreshToken());
        return login.getStatus().getType().contains("Success");
    }

    public boolean register(String email, String password, String zipCode, String country) {
        return register(email, password, zipCode, country, DEFAULT_DEVICE_ID);
    }

    public boolean register(String email, String password, String zipCode, String country, String deviceId) {
        String url = "https://eu-prod.api.mcd.com/exp/v1/customer/registration";
        String body = gson.toJson(new RegisterRequest(email, password, zipCode, country, deviceId));
        LoginResponse register = gson.fromJson(queryPost(url, ByteArrayContent.fromString("application/json", body)), LoginResponse.class);
        return register.getStatus().getType().contains("Success");
    }

    public boolean activateEmail(String email, String activationCode) {
        return activate(email, activationCode, null, "email");
    }

    public boolean activateEmail(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "email");
    }

    public boolean activateDevice(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "device");
    }

    private boolean activate(String email, String activationCode, String deviceId, String type) {
        String url = "https://eu-prod.api.mcd.com/exp/v1/customer/activation";
        String body = gson.toJson(new ActivationRequest(email, activationCode, deviceId, type));
        Response activate = gson.fromJson(queryPut(url, ByteArrayContent.fromString("application/json", body)), Response.class);
        return activate.getStatus().getType().contains("Success");
    }

    public ProfileResponse getProfile() {
        String url = "https://eu-prod.api.mcd.com/exp/v1/customer/profile";
        return gson.fromJson(queryGet(url), ProfileResponse.class);
    }

    public RestaurantResponse getRestaurants(double latitude, double longitude, int distance, int amount) {
        String url = String.format("https://eu-prod.api.mcd.com/exp/v1/restaurant/location?distance=%s&filter=summary" +
                "&latitude=%s&longitude=%s&pageSize=%s", distance, latitude, longitude, amount);
        return gson.fromJson(queryGet(url), RestaurantResponse.class);
    }

    public PointsResponse getPoints() {
        String url = "https://eu-prod.api.mcd.com/exp/v1/loyalty/customer/points";
        return gson.fromJson(queryGet(url), PointsResponse.class);
    }

    public OfferResponse getOffers() {
        String url = "https://eu-prod.api.mcd.com/exp/v1/offers?distance=80&latitude=53.557&longitude=10.006&optOuts=&timezoneOffsetInMinutes=120";
        return gson.fromJson(queryGet(url), OfferResponse.class);
    }

    public OfferDetailsResponse getOfferDetails(String propositionId) {
        String url = "https://eu-prod.api.mcd.com/exp/v1/offers/details/" + propositionId;
        return gson.fromJson(queryGet(url), OfferDetailsResponse.class);
    }

    public RedeemResponse redeemCoupon(String propositionId) {
        return redeemCoupon(propositionId, null);
    }

    public RedeemResponse redeemCoupon(String propositionId, String offerId) {
        String url = "https://eu-prod.api.mcd.com/exp/v1/offers/redemption/" + propositionId + (offerId != null ? "?offerId=" + offerId : "");
        return gson.fromJson(queryGet(url), RedeemResponse.class);
    }

    public Response joinMyMcDonalds() {
        ProfileResponse profileResponse = getProfile();
        if(profileResponse.getStatus().getType().contains("Success")) {
            String userId = profileResponse.getInfo().getHashedDcsId();
            String email = profileResponse.getInfo().getBase().getUsername();
            String url = "https://eu-prod.api.mcd.com/exp/v1/customer/profile";
            String body = gson.toJson(new MyMcDonaldsRequest(userId, email));
            return gson.fromJson(queryPut(url, ByteArrayContent.fromString("application/json", body)), Response.class);
        }
        return null;
    }

    public CalendarAddressResponse uploadAddress(Map<String, String> form) {
        return uploadAddress(form, DEFAULT_DEVICE_ID);
    }

    public CalendarAddressResponse uploadAddress(Map<String, String> form, String deviceId) {
        String url = "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/appcalendar/uploadAddress";
        form.put("token", auth.getAccessToken().replace("Bearer ", ""));
        return gson.fromJson(queryPost(url, new UrlEncodedContent(form)), CalendarAddressResponse.class);
    }

    public CalendarStateResponse getUserState() {
        ProfileResponse profileResponse = getProfile();
        if(profileResponse.getStatus().getType().contains("Success")) {
            String userId = profileResponse.getInfo().getHashedDcsId();
            String email = profileResponse.getInfo().getBase().getUsername();
            String token = auth.getAccessToken().replace("Bearer ", "");

            String stateUrl = String.format("https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/appcalendar/"
                            + "getUserState?userId=%s&token=%s&userName=%s&deviceId=%s&formId=", userId, token,
                    URLEncoder.encode(email, StandardCharsets.UTF_8), DEFAULT_DEVICE_ID);
            return gson.fromJson(queryGet(stateUrl), CalendarStateResponse.class);
        }
        return new CalendarStateResponse();
    }

    public CalendarResponse participateCalendar() {
        ProfileResponse profileResponse = getProfile();
        if(profileResponse.getStatus().getType().contains("Success")) {
            String userId = profileResponse.getInfo().getHashedDcsId();
            String email = profileResponse.getInfo().getBase().getUsername();
            String token = auth.getAccessToken().replace("Bearer ", "");

            // Find out, if we have participated
            String statusUrl = "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/appcalendar/status";
            String statusBody = gson.toJson(new CalendarRequest(token, email, userId));
            CalendarResponse statusResponse = gson.fromJson(queryPost(statusUrl,
                    ByteArrayContent.fromString("application/json", statusBody)), CalendarResponse.class);

            if(statusResponse.success() && !statusResponse.hasParticipated()) {
                String prizeId = statusResponse.getPrize().getPrizeId();
                String partUrl = "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/appcalendar/participate";
                String partBody = gson.toJson(new CalendarRequest(token, email, userId, prizeId));
                return gson.fromJson(queryPut(partUrl, ByteArrayContent.fromString("application/json", partBody)),
                        CalendarResponse.class);
            }
        }
        return new CalendarResponse();
    }
}
