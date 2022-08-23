package icu.jnet.mcd.api.request;

import icu.jnet.mcd.annotation.SensorRequired;
import icu.jnet.mcd.api.entity.components.Address;
import icu.jnet.mcd.api.entity.response.Profile;
import icu.jnet.mcd.api.entity.components.Subscription;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SensorRequired
public class ProfileRequest implements Request {

    private final Profile profile = new Profile();

    public ProfileRequest setZipCode(String zipCode) {
        profile.setAddress(new Address(zipCode));
        return this;
    }

    public ProfileRequest useMyMcDonalds(boolean b) {
        String time = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("Z", "");
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(time, b ? "N" : "Y", "Secondary Processing", "21"));
        subscriptions.add(new Subscription(time, b ? "Y" : "N", "Personal Marketing", "23"));
        subscriptions.add(new Subscription(time, b ? "Y" : "N", "Loyalty Enrollment", "24"));
        subscriptions.add(new Subscription(time, b ? "Y" : "N", "Loyalty Communication", "25"));
        profile.setSubscriptions(subscriptions);
        return this;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/profile";
    }
}