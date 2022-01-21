package icu.jnet.mcd.api;

import icu.jnet.mcd.api.request.*;
import icu.jnet.mcd.api.response.*;

import javax.annotation.Nonnull;
import java.util.Map;

public class McClient extends McBase {

    public static final String DEFAULT_DEVICE_ID = "75408e58622a88c6";
    private String userId, zipCode;

    public boolean login(@Nonnull String email, @Nonnull String password) {
        return login(email, password, DEFAULT_DEVICE_ID);
    }

    public boolean login(@Nonnull String email, @Nonnull String password, @Nonnull String deviceId) {
        Request request = new LoginRequest(email, password, deviceId);
        LoginResponse login = queryPost(request, LoginResponse.class);
        auth.updateAccessToken(login.getAccessToken());
        auth.updateRefreshToken(login.getRefreshToken());
        if(login.getStatus().getType().contains("Success")) {
            ProfileResponse profileResponse = getProfile();
            if(profileResponse.getStatus().getType().contains("Success")) {
                this.email = email;
                this.userId = profileResponse.getInfo().getHashedDcsId();
                this.zipCode = profileResponse.getInfo().getZipCode();
                return true;
            }
        }
        return false;
    }

    public boolean register(String email, String password, String zipCode, String country) {
        return register(email, password, zipCode, country, DEFAULT_DEVICE_ID);
    }

    public boolean register(String email, String password, String zipCode, String country, String deviceId) {
        Request request = new RegisterRequest(email, password, zipCode, country, deviceId);
        LoginResponse register = queryPost(request, LoginResponse.class);
        return register.getStatus().getType().contains("Success");
    }

    public boolean activateAccount(String email, String activationCode) {
        return activate(email, activationCode, null, "email");
    }

    public boolean activateAccount(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "email");
    }

    public boolean activateDevice(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "device");
    }

    private boolean activate(String email, String activationCode, String deviceId, String type) {
        Request request = new ActivationRequest(email, activationCode, deviceId, type);
        Response activate = queryPut(request, Response.class);
        return activate.getStatus().getType().contains("Success");
    }

    public ProfileResponse getProfile() {
        return queryGet(new ProfileRequest(), ProfileResponse.class);
    }

    public RestaurantResponse getRestaurants(double latitude, double longitude, int distance, int amount) {
        return queryGet(new RestaurantRequest(latitude, longitude, distance, amount), RestaurantResponse.class);
    }

    public PointsResponse getPoints() {
        return queryGet(new PointsRequest(), PointsResponse.class);
    }

    public OfferResponse getOffers() {
        return queryGet(new OffersRequest(), OfferResponse.class);
    }

    public OfferDetailsResponse getOfferDetails(String propositionId) {
        return queryGet(new OfferDetailsRequest(propositionId), OfferDetailsResponse.class);
    }

    public RedeemResponse redeemCoupon(String propositionId) {
        return redeemCoupon(propositionId, null);
    }

    public RedeemResponse redeemCoupon(String propositionId, String offerId) {
        return queryGet(new RedeemRequest(propositionId, offerId), RedeemResponse.class);
    }

    public Response joinMyMcDonalds() {
        return queryPut(new ProfileRequest(userId, email, zipCode), Response.class);
    }

    public Response setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return queryPut(new ProfileRequest(userId, email, zipCode), Response.class);
    }

    public CalendarAddressResponse uploadAddress(Map<String, String> form) {
        form.put("token", auth.getAccessToken().replace("Bearer ", ""));
        return queryPost(new CalendarAddressRequest(form), CalendarAddressResponse.class);
    }

    public CalendarStateResponse getUserState() {
        String token = auth.getAccessToken().replace("Bearer ", "");
        Request request = new CalendarStateRequest(userId, email, token, DEFAULT_DEVICE_ID);
        return queryGet(request, CalendarStateResponse.class);
    }

    public CalendarResponse participateCalendar() {
        String token = auth.getAccessToken().replace("Bearer ", "");
        // Find out, if we have participated
        Request request = new CalendarStatusRequest(token, email, userId);
        CalendarResponse statusResponse = queryPost(request, CalendarResponse.class);

        if(statusResponse.success() && !statusResponse.hasParticipated()) {
            String prizeId = statusResponse.getPrize().getPrizeId();
            request = new CalendarRequest(token, email, userId, prizeId);
            return queryPut(request, CalendarResponse.class);
        }
        return new CalendarResponse();
    }
}
