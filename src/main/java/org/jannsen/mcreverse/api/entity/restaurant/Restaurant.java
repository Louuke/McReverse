package org.jannsen.mcreverse.api.entity.restaurant;

import java.util.List;

public class Restaurant {

    private Address address;
    private Location location;
    private List<String> facilities;
    private List<OpeningHours> weekOpeningHours;
    private String name, phoneNumber, restaurantStatus, timeZone;
    private int nationalStoreNumber, status;

    public Address getAddress() {
        return address;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public int getStatus() {
        return status;
    }

    public int getNationalStoreNumber() {
        return nationalStoreNumber;
    }

    public List<OpeningHours> getWeekOpeningHours() {
        return weekOpeningHours;
    }
}
