package icu.jnet.mcd.api.entity.profile;

public class Subscription {

    private final String optInStatus, subscriptionDesc, subscriptionId;
    private String optInDate, optOutDate;

    public Subscription(String optDate, String optInStatus, String subscriptionDesc, String subscriptionId) {
        this.optInStatus = optInStatus;
        this.subscriptionDesc = subscriptionDesc;
        this.subscriptionId = subscriptionId;

        if(optInStatus.equals("Y")) {
            this.optInDate = optDate;
        } else {
            this.optOutDate = optDate;
        }
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getOptInStatus() {
        return optInStatus;
    }

    public String getSubscriptionDesc() {
        return subscriptionDesc;
    }
}
