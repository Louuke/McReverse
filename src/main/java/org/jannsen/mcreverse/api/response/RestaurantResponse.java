package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.entity.restaurant.Restaurant;
import org.jannsen.mcreverse.api.response.status.Status;

import java.util.List;
import java.util.Map;

public class RestaurantResponse extends Response {

    private Map<String, List<Restaurant>> response;

    public RestaurantResponse(Status status) {
        super(status);
    }

    public List<Restaurant> getRestaurants() {
        return response.get("restaurants");
    }
}

