package icu.jnet.mcd.api;

import com.google.api.client.http.ByteArrayContent;
import icu.jnet.mcd.api.request.ActivationRequest;
import icu.jnet.mcd.api.request.LoginRequest;
import icu.jnet.mcd.api.request.RegisterRequest;
import icu.jnet.mcd.api.response.*;

public class MacClient extends MacBase {

    public LoginResponse login(String email, String password) {
        String url2 = "/exp/v1/customer/login";
        String body = gson.toJson(new LoginRequest(email, password));
        LoginResponse login = gson.fromJson(queryPost(url2, ByteArrayContent.fromString("application/json", body)), LoginResponse.class);
        auth.updateAccessToken(login.getAccessToken());
        auth.updateRefreshToken(login.getRefreshToken());
        return login;
    }

    public LoginResponse register(String email, String password, String zipCode, String country) {
        String url = "/exp/v1/customer/registration";
        String body = gson.toJson(new RegisterRequest(email, password, zipCode, country));
        return gson.fromJson(queryPost(url, ByteArrayContent.fromString("application/json", body)), LoginResponse.class);
    }

    public Response activate(String email, String activationCode) {
        String url = "/exp/v1/customer/activation";
        String body = gson.toJson(new ActivationRequest(email, activationCode));
        return gson.fromJson(queryPut(url, ByteArrayContent.fromString("application/json", body)), Response.class);
    }

    public RestaurantResponse getRestaurants(double latitude, double longitude, int distance, int amount) {
        String url = String.format("/exp/v1/restaurant/location?distance=%s&filter=summary&latitude=%s&longitude=%s" +
                "&pageSize=%s", distance, latitude, longitude, amount);
        return gson.fromJson(queryGet(url), RestaurantResponse.class);
    }

    public PointsResponse getPoints() {
        String url = "/exp/v1/loyalty/customer/points";
        return gson.fromJson(queryGet(url), PointsResponse.class);
    }

    public OfferResponse getOffers() {
        String url = "/exp/v1/offers?distance=80&latitude=53.557&longitude=10.006&optOuts=&timezoneOffsetInMinutes=120";
        return gson.fromJson(queryGet(url), OfferResponse.class);
    }

    public RedeemResponse redeemCoupon(OfferResponse.Offer offer) {
        String url = "/exp/v1/offers/redemption/" + offer.getOfferPropositionId()
                + (offer.getOfferId() != 0 ? "?offerId=" + offer.getOfferId() : "");
        return gson.fromJson(queryGet(url), RedeemResponse.class);
    }
}
