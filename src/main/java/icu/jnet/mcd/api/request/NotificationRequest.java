package icu.jnet.mcd.api.request;

public class NotificationRequest extends Request {

    private final String device = "c9ZdzkqwfgY:APA91bE910Kbyi2japDOTgwPtLM7Sl3UEMg_XdmtjSBpwRTXo4FH-_2pCtVzlbvbNPMNkRJN41B-5LZnK7ZDGH3namwOAXrjh-rhfVfSTXkh9DdFCZv99Joew7LJKokiUiUFuwdKZhnV",
        platform = "android";

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/notification/platformapplication";
    }
}
