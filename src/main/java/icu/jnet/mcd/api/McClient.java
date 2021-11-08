package icu.jnet.mcd.api;

import com.google.api.client.http.ByteArrayContent;
import icu.jnet.mcd.api.request.ActivationRequest;
import icu.jnet.mcd.api.request.AnniversaryRequest;
import icu.jnet.mcd.api.request.LoginRequest;
import icu.jnet.mcd.api.request.RegisterRequest;
import icu.jnet.mcd.api.response.*;

public class McClient extends McBase {

    public boolean login(String email, String password) {
        String url2 = "https://eu-prod.api.mcd.com/exp/v1/customer/login";
        String body = gson.toJson(new LoginRequest(email, password));
        LoginResponse login = gson.fromJson(queryPost(url2, ByteArrayContent.fromString("application/json", body)), LoginResponse.class);
        auth.updateAccessToken(login.getAccessToken());
        auth.updateRefreshToken(login.getRefreshToken());
        return login.getStatus().getType().contains("Success");
    }

    public boolean register(String email, String password, String zipCode, String country) {
        String url = "https://eu-prod.api.mcd.com/exp/v1/customer/registration";
        String body = gson.toJson(new RegisterRequest(email, password, zipCode, country));
        LoginResponse register = gson.fromJson(queryPost(url, ByteArrayContent.fromString("application/json", body)), LoginResponse.class);
        return register.getStatus().getType().contains("Success");
    }

    public boolean activate(String email, String activationCode) {
        String url = "https://eu-prod.api.mcd.com/exp/v1/customer/activation";
        String body = gson.toJson(new ActivationRequest(email, activationCode));
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
        String url = "https://eu-prod.api.mcd.com/exp/v1/offers/redemption/" + propositionId;
        return gson.fromJson(queryGet(url), RedeemResponse.class);
    }

    public AnniversaryResponse participateAnniversary() {
        ProfileResponse profileResponse = getProfile();
        if(profileResponse.getStatus().getType().contains("Success")) {
            String userId = profileResponse.getInfo().getHashedDcsId();
            String email = profileResponse.getInfo().getBase().getUsername();
            String statusUrl = "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/peak/status";
            String statusBody = gson.toJson(new AnniversaryRequest(auth.getAccessToken().replace("Bearer ", ""), email, userId));
            AnniversaryResponse anniversaryResponse = gson.fromJson(queryPost(statusUrl,
                    ByteArrayContent.fromString("application/json", statusBody)), AnniversaryResponse.class);
            if(anniversaryResponse.getPrize() != null) {
                String prizeId = anniversaryResponse.getPrize().getPrizeId();
                String partUrl = "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/peak/participate";
                String partBody = gson.toJson(new AnniversaryRequest.AnniversaryPartRequest(auth.getAccessToken().replace("Bearer ", ""),
                        email, userId, prizeId));
                return gson.fromJson(queryPut(partUrl, ByteArrayContent.fromString("application/json", partBody)), AnniversaryResponse.class);
            }
        }
        return new AnniversaryResponse();
    }
}
