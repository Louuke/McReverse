package org.jannsen.mcreverse.api;

import com.google.api.client.http.HttpMethods;
import org.jannsen.mcreverse.api.entity.login.Credentials;
import org.jannsen.mcreverse.api.entity.register.RegisterOptions;
import org.jannsen.mcreverse.api.request.*;
import org.jannsen.mcreverse.api.response.*;
import org.jannsen.mcreverse.utils.listener.ClientActionListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class McClient extends McBase implements ClientActionListener {

    public McClient() {
        addActionListener(this);
    }

    public LoginResponse login(@Nonnull String email, @Nonnull String password, @Nonnull String deviceId) {
        setEmail(email);
        LoginResponse login = query(new LoginRequest(email, password, deviceId), HttpMethods.POST, LoginResponse.class);
        if(login.success()) {
            setAuthorization(login.getResponse());
        }
        return login;
    }

    public RegisterResponse register(@Nonnull String email, @Nonnull String password) {
        return register(email, password, new RegisterOptions());
    }

    public RegisterResponse register(@Nonnull String email, @Nonnull String password, @Nonnull RegisterOptions options) {
        setEmail(email);
        Response response = query(new RegisterRequest(email, password, options), HttpMethods.POST, LoginResponse.class);
        return new RegisterResponse(response.getStatus(), options.getDeviceId());
    }

    public Response activateAccount(@Nonnull String email, @Nonnull String activationCode, @Nonnull String deviceId) {
        return activate(email, activationCode, deviceId, Credentials.Type.EMAIL);
    }

    public Response activateDevice(@Nonnull String email, @Nonnull String activationCode, @Nonnull String deviceId) {
        return activate(email, activationCode, deviceId, Credentials.Type.DEVICE);
    }

    private Response activate(String email, String activationCode, String deviceId, String type) {
        setEmail(email);
        return query(new ActivationRequest(email, activationCode, deviceId, type), HttpMethods.PUT, Response.class);
    }

    public Response deleteAccount() {
        return query(new DeleteRequest(), HttpMethods.DELETE, Response.class);
    }

    public ProfileResponse getProfile() {
        return query(new ProfileRequest(), HttpMethods.GET, ProfileResponse.class);
    }

    public RestaurantResponse getRestaurants(double latitude, double longitude, int distance, int amount) {
        return query(new RestaurantRequest(latitude, longitude, distance, amount), HttpMethods.GET, RestaurantResponse.class);
    }

    public PointsResponse getPoints() {
        return query(new PointsRequest(), HttpMethods.GET, PointsResponse.class);
    }

    public OfferResponse getOffers() {
        return query(new OffersRequest(), HttpMethods.GET, OfferResponse.class);
    }

    public OfferDetailsResponse getOfferDetails(int propositionId) {
        return query(new OfferDetailsRequest(propositionId), HttpMethods.GET, OfferDetailsResponse.class);
    }

    public RedeemResponse redeemCoupon(int propositionId) {
        return redeemCoupon(propositionId, 0);
    }

    public RedeemResponse redeemCoupon(int propositionId, long offerId) {
        return query(new RedeemRequest(propositionId, offerId), HttpMethods.GET, RedeemResponse.class);
    }

    public RedeemResponse getIdentificationCode() {
        return query(new IdentRequest(), HttpMethods.GET, RedeemResponse.class);
    }

    public BonusPointsResponse getPointsBonuses() {
        return query(new BonusPointsRequest(), HttpMethods.GET, BonusPointsResponse.class);
    }

    public OptInResponse optInCampaign(int campaignId) {
        return query(new OptInRequest(campaignId), HttpMethods.POST, OptInResponse.class);
    }

    public Response useMyMcDonalds(boolean enabled) {
        return query(new ProfileRequest().useMyMcDonalds(enabled), HttpMethods.PUT, Response.class);
    }

    public Response changeName(@Nullable String firstName, @Nullable String lastName) {
        return query(new ProfileRequest().setFirstName(firstName).setLastName(lastName), HttpMethods.PUT, Response.class);
    }

    public Response changeZipCode(String zipCode) {
        return query(new ProfileRequest().setZipCode(zipCode), HttpMethods.PUT, Response.class);
    }

    public Response setLocation() {
        return query(new LocationRequest(getEmail()), HttpMethods.POST, Response.class);
    }

    public Response setNotification() {
        return query(new NotificationRequest(), HttpMethods.POST, Response.class);
    }

    public OfferImageResponse getOfferImage(String imageBaseName) {
        return query(new OfferImageRequest(imageBaseName), HttpMethods.GET, OfferImageResponse.class);
    }

    @Override
    public void authRefreshRequired() {
        LoginResponse response = query(new RefreshRequest(getAuthorization().getRefreshToken()), HttpMethods.POST, LoginResponse.class);
        if(response.success()) setAuthorization(response.getResponse());
    }
}
