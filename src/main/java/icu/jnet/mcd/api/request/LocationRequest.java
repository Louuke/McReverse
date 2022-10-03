package icu.jnet.mcd.api.request;

public class LocationRequest extends Request {

    private final String application = "MOT", eventType = "changeLocation", languageName = "de-DE", platform = "android",
            deviceToken = "c9ZdzkqwfgY:APA91bE910Kbyi2japDOTgwPtLM7Sl3UEMg_XdmtjSBpwRTXo4FH-_2pCtVzlbvbNPMNkRJN41B-5LZnK7ZDGH3namwOAXrjh-rhfVfSTXkh9DdFCZv99Joew7LJKokiUiUFuwdKZhnV",
            timeZone = "Europe/Berlin", emailAddress;
    private final int closestRestaurantId = 27601289;

    public LocationRequest(String email) {
        this.emailAddress = email;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/location/event";
    }
}
