package icu.jnet.mcd.api;

import com.google.api.client.http.HttpMethods;
import icu.jnet.mcd.api.request.*;
import icu.jnet.mcd.api.response.*;

import java.util.Arrays;
import java.util.Objects;

public class McClient extends McBase {

    public LoginResponse login(String email, String password) {
        return login(email, password, getUserInfo().getDeviceId());
    }

    public LoginResponse login(String email, String password, String deviceId) {
        getUserInfo().setEmail(email).setDeviceId(deviceId);
        LoginResponse login = query(new LoginRequest(email, password, deviceId), LoginResponse.class, HttpMethods.POST);
        if(login.success()) {
            setAuthorization(login.getResponse());
            ProfileResponse profileResponse = getProfile();
            if(profileResponse.success()) {
                getUserInfo().setUserId(profileResponse.getResponse().getHashedDcsId());
            }
        }
        return login;
    }

    public Response register(String email, String password, String zipCode) {
        return register(email, password, zipCode, getUserInfo().getDeviceId());
    }

    public Response register(String email, String password, String zipCode, String deviceId) {
        return query(new RegisterRequest(email, password, zipCode, deviceId), LoginResponse.class, HttpMethods.POST);
    }

    public Response activateAccount(String email, String activationCode) {
        return activate(email, activationCode, getUserInfo().getDeviceId(), "email");
    }

    public Response activateAccount(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "email");
    }

    public Response activateDevice(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "device");
    }

    private Response activate(String email, String activationCode, String deviceId, String type) {
        return query(new ActivationRequest(email, activationCode, deviceId, type), Response.class, HttpMethods.PUT);
    }

    public Response deleteAccount() {
        return query(new DeleteRequest(), Response.class, HttpMethods.DELETE);
    }

    public ProfileResponse getProfile() {
        return query(new ProfileRequest(), ProfileResponse.class, HttpMethods.GET);
    }

    public RestaurantResponse getRestaurants(double latitude, double longitude, int distance, int amount) {
        return query(new RestaurantRequest(latitude, longitude, distance, amount), RestaurantResponse.class, HttpMethods.GET);
    }

    public PointsResponse getPoints() {
        return query(new PointsRequest(), PointsResponse.class, HttpMethods.GET);
    }

    public OfferResponse getOffers() {
        return query(new OffersRequest(), OfferResponse.class, HttpMethods.GET);
    }

    public OfferDetailsResponse getOfferDetails(int propositionId) {
        return query(new OfferDetailsRequest(propositionId), OfferDetailsResponse.class, HttpMethods.GET);
    }

    public RedeemResponse redeemCoupon(int propositionId) {
        return redeemCoupon(propositionId, 0);
    }

    public RedeemResponse redeemCoupon(int propositionId, long offerId) {
        return query(new RedeemRequest(propositionId, offerId), RedeemResponse.class, HttpMethods.GET);
    }

    public RedeemResponse getIdentificationCode() {
        return query(new IdentRequest(), RedeemResponse.class, HttpMethods.GET);
    }

    public BonusPointsResponse getPointsBonuses() {
        return query(new BonusPointsRequest(), BonusPointsResponse.class, HttpMethods.GET);
    }

    public OptInResponse optInCampaign(int campaignId) {
        return query(new OptInRequest(campaignId), OptInResponse.class, HttpMethods.POST);
    }

    public Response useMyMcDonalds(boolean b) {
        return query(new ProfileRequest().useMyMcDonalds(b), Response.class, HttpMethods.PUT);
    }

    public Response setZipCode(String zipCode) {
        return query(new ProfileRequest().setZipCode(zipCode), Response.class, HttpMethods.PUT);
    }

    public boolean usesMyMcDonalds() {
        return query(new ProfileRequest(), ProfileResponse.class, HttpMethods.GET).getResponse()
                .getSubscriptions().stream()
                .filter(sub -> sub.getOptInStatus().equals("Y")
                        && Arrays.asList("23", "24", "25").contains(sub.getSubscriptionId())
                        || sub.getOptInStatus().equals("N")
                        && sub.getSubscriptionId().equals("21")).count() == 4;
    }

    public Response setLocation() {
        return query(new LocationRequest(getEmail()), Response.class,HttpMethods.POST);
    }

    public Response setNotification() {
        return query(new NotificationRequest(), Response.class, HttpMethods.POST);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof McClient client)) {
            return false;
        }
        return client.getEmail().equals(getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmail());
    }
}
