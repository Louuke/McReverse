package icu.jnet.mcd.api;

import com.google.api.client.http.ByteArrayContent;
import icu.jnet.mcd.api.request.*;
import icu.jnet.mcd.api.response.*;

import javax.annotation.Nonnull;

public class McClient extends McBase {

    public static final String DEFAULT_DEVICE_ID = "fa24c95cc4475881";

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
        return register(email, password, zipCode, country, null);
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

    public CalendarResponse participateCalendar() {
        ProfileResponse profileResponse = getProfile();
        if(profileResponse.getStatus().getType().contains("Success")) {
            String userId = profileResponse.getInfo().getHashedDcsId();
            String email = profileResponse.getInfo().getBase().getUsername();
            String token = auth.getAccessToken().replace("Bearer ", "");

            // Fetch user state. Purpose is unknown
            String stateUrl = String.format("https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/appcalendar/"
                    + "getUserState?userId=%s&token=%s", userId, token);
            CalendarStateResponse stateResponse = gson.fromJson(queryGet(stateUrl), CalendarStateResponse.class);
            if(stateResponse.success() && !stateResponse.getInstantPrizes().isEmpty()) {
                stateResponse.getInstantPrizes().forEach(s -> System.out.println("INSTANT PRIZE: " + email + " - " + gson.toJson(s)));
            }
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
