package icu.jnet.mcd.api;

import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.api.request.*;
import icu.jnet.mcd.api.response.*;
import icu.jnet.mcd.utils.listener.Action;
import icu.jnet.mcd.utils.listener.ClientStateListener;

import java.util.Arrays;
import java.util.Objects;

public class McClient extends McBase implements ClientStateListener {

    public static final String DEFAULT_DEVICE_ID = "75408e58622a88c6";

    public McClient() {
        addStateListener(this);
    }

    public LoginResponse login(String email, String password) {
        return login(email, password, DEFAULT_DEVICE_ID);
    }

    public LoginResponse login(String email, String password, String deviceId) {
        LoginResponse login = query(new LoginRequest(email, password, deviceId), LoginResponse.class, Request.Type.POST);
        if(login.success()) {
            setAuthorization(login.getResponse());
            ProfileResponse profileResponse = getProfile();
            if(profileResponse.success()) {
                getUserInfo().setEmail(email).setDeviceId(deviceId).setUserId(profileResponse.getResponse().getHashedDcsId());
            }
        }
        return login;
    }

    public Response register(String email, String password, String zipCode) {
        return register(email, password, zipCode, DEFAULT_DEVICE_ID);
    }

    public Response register(String email, String password, String zipCode, String deviceId) {
        return query(new RegisterRequest(email, password, zipCode, deviceId), LoginResponse.class, Request.Type.POST);
    }

    public Response activateAccount(String email, String activationCode) {
        return activate(email, activationCode, DEFAULT_DEVICE_ID, "email");
    }

    public Response activateAccount(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "email");
    }

    public Response activateDevice(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "device");
    }

    private Response activate(String email, String activationCode, String deviceId, String type) {
        return query(new ActivationRequest(email, activationCode, deviceId, type), Response.class, Request.Type.PUT);
    }

    public Response deleteAccount() {
        return query(new DeleteRequest(), Response.class, Request.Type.DELETE);
    }

    public ProfileResponse getProfile() {
        return query(new ProfileRequest(), ProfileResponse.class, Request.Type.GET);
    }

    public RestaurantResponse getRestaurants(double latitude, double longitude, int distance, int amount) {
        return query(new RestaurantRequest(latitude, longitude, distance, amount), RestaurantResponse.class, Request.Type.GET);
    }

    public PointsResponse getPoints() {
        return query(new PointsRequest(), PointsResponse.class, Request.Type.GET);
    }

    public OfferResponse getOffers() {
        return query(new OffersRequest(), OfferResponse.class, Request.Type.GET);
    }

    public OfferDetailsResponse getOfferDetails(int propositionId) {
        return query(new OfferDetailsRequest(propositionId), OfferDetailsResponse.class, Request.Type.GET);
    }

    public RedeemResponse redeemCoupon(int propositionId) {
        return redeemCoupon(propositionId, 0);
    }

    public RedeemResponse redeemCoupon(int propositionId, long offerId) {
        return query(new RedeemRequest(propositionId, offerId), RedeemResponse.class, Request.Type.GET);
    }

    public RedeemResponse getIdentificationCode() {
        return query(new IdentRequest(), RedeemResponse.class, Request.Type.GET);
    }

    public BonusPointsResponse getPointsBonuses() {
        return query(new BonusPointsRequest(), BonusPointsResponse.class, Request.Type.GET);
    }

    public OptInResponse optInCampaign(int campaignId) {
        return query(new OptInRequest(campaignId), OptInResponse.class, Request.Type.POST);
    }

    public Response useMyMcDonalds(boolean b) {
        return query(new ProfileRequest().useMyMcDonalds(b), Response.class, Request.Type.PUT);
    }

    public Response setZipCode(String zipCode) {
        return query(new ProfileRequest().setZipCode(zipCode), Response.class, Request.Type.PUT);
    }

    public boolean usesMyMcDonalds() {
        return query(new ProfileRequest(), ProfileResponse.class, Request.Type.GET).getResponse()
                .getSubscriptions().stream()
                .filter(sub -> sub.getOptInStatus().equals("Y")
                        && Arrays.asList("23", "24", "25").contains(sub.getSubscriptionId())
                        || sub.getOptInStatus().equals("N")
                        && sub.getSubscriptionId().equals("21")).count() == 4;
    }

    public Response setLocation() {
        return query(new LocationRequest(getEmail()), Response.class, Request.Type.POST);
    }

    public Response setNotification() {
        return query(new NotificationRequest(), Response.class, Request.Type.POST);
    }

    @Override
    public String basicBearerRequired() {
        return query(new BasicBearerRequest(), BasicBearerResponse.class, Request.Type.POST).getToken();
    }

    @Override
    public Authorization jwtExpired() {
        LoginResponse login = query(new RefreshRequest(getAuthorization().getRefreshToken()), LoginResponse.class, Request.Type.POST);
        if(login.success()) {
            setAuthorization(login.getResponse());
            getActionModel().notifyListener(Action.AUTHORIZATION_CHANGED);
            return getAuthorization();
        }
        return null;
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
