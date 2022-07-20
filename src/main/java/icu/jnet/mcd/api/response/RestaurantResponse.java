package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.HashMap;
import java.util.List;

public class RestaurantResponse extends Response {

    private HashMap<String, List<Restaurant>> response;

    public RestaurantResponse(Status status) {
        super(status);
    }

    public List<Restaurant> getRestaurants() {
        return response.get("restaurants");
    }

    public static class Restaurant {

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

    public static class Address {

        private String addressLine1, cityTown, country, postalZip;

        public String getAddressLine() {
            return addressLine1;
        }

        public String getCityTown() {
            return cityTown;
        }

        public String getCountry() {
            return country;
        }

        public String getPostalZip() {
            return postalZip;
        }
    }

    public static class Location {

        private double latitude, longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    public static class OpeningHours {

        private int dayOfWeekId;
        private List<Services> services;

        public int getDayOfWeekId() {
            return dayOfWeekId;
        }

        public List<Services> getServices() {
            return services;
        }
    }

    public static class Services {

        private String endTime, serviceName, startTime;
        private boolean isOpen;

        public String getEndTime() {
            return endTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getServiceName() {
            return serviceName;
        }

        public boolean isOpen() {
            return isOpen;
        }
    }
}

