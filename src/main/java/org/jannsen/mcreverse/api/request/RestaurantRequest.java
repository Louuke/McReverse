package org.jannsen.mcreverse.api.request;

public class RestaurantRequest extends Request {

    private final double latitude, longitude;
    private final int distance, amount;

    public RestaurantRequest(double latitude, double longitude, int distance, int amount) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.amount = amount;
    }

    @Override
    public String getUrl() {
        String url = "https://eu-prod.api.mcd.com/exp/v1/restaurant/location?distance=%s&filter=summary&latitude=%s&longitude=%s&pageSize=%s";
        return String.format(url, distance, latitude, longitude, amount);
    }
}
