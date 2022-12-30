package org.jannsen.mcreverse.api.entity.profile;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return subscriptionId.equals(that.subscriptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionId);
    }
}
