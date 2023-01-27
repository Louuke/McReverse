package org.jannsen.mcreverse.api;

import com.google.api.client.http.HttpMethods;
import org.jannsen.mcreverse.api.entity.login.Credentials;
import org.jannsen.mcreverse.api.entity.register.RegisterOptions;
import org.jannsen.mcreverse.api.request.*;
import org.jannsen.mcreverse.api.response.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class McClient extends McBase {

    public LoginResponse login(@Nonnull String email, @Nonnull String password, @Nonnull String deviceId) {
        setEmail(email);
        LoginResponse login = query(new LoginRequest(email, password, deviceId), LoginResponse.class, HttpMethods.POST);
        if(!login.success()) {
            return login;
        }
        setAuthorization(login.getResponse());
        return login;
    }

    public RegisterResponse register(@Nonnull String email, @Nonnull String password) {
        return register(email, password, new RegisterOptions());
    }

    public RegisterResponse register(@Nonnull String email, @Nonnull String password, @Nonnull RegisterOptions options) {
        setEmail(email);
        Response response = query(new RegisterRequest(email, password, options), LoginResponse.class, HttpMethods.POST);
        return new RegisterResponse(response.getStatus(), options.getDeviceId());
    }

    public Response activateAccount(@Nonnull String email, @Nonnull String activationCode, @Nonnull String deviceId) {
        return activate(email, activationCode, deviceId, Credentials.Type.EMAIL);
    }

    public Response activateDevice(@Nonnull String email, @Nonnull String activationCode, @Nonnull String deviceId) {
        return activate(email, activationCode, deviceId, Credentials.Type.DEVICE);
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

    public Response useMyMcDonalds(boolean enabled) {
        return query(new ProfileRequest().useMyMcDonalds(enabled), Response.class, HttpMethods.PUT);
    }

    public Response changeName(@Nullable String firstName, @Nullable String lastName) {
        return query(new ProfileRequest().setFirstName(firstName).setLastName(lastName), Response.class, HttpMethods.PUT);
    }

    public Response changeZipCode(String zipCode) {
        return query(new ProfileRequest().setZipCode(zipCode), Response.class, HttpMethods.PUT);
    }

    public Response setLocation() {
        return query(new LocationRequest(getEmail()), Response.class,HttpMethods.POST);
    }

    public Response setNotification() {
        return query(new NotificationRequest(), Response.class, HttpMethods.POST);
    }
}
