package org.jannsen.mcreverse.api.request;

import org.jannsen.mcreverse.annotation.SensorRequired;
import org.jannsen.mcreverse.api.entity.profile.Address;
import org.jannsen.mcreverse.api.entity.profile.CustomerInformation;
import org.jannsen.mcreverse.api.entity.profile.Subscription;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SensorRequired
public class ProfileRequest extends Request {

    private final CustomerInformation customerInformation = new CustomerInformation();

    public ProfileRequest setZipCode(@Nullable String zipCode) {
        customerInformation.setAddress(new Address(zipCode));
        return this;
    }

    public ProfileRequest setFirstName(@Nullable String firstName) {
        customerInformation.getBase().setFirstName(firstName);
        return this;
    }

    public ProfileRequest setLastName(@Nullable String lastName) {
        customerInformation.getBase().setLastName(lastName);
        return this;
    }

    public ProfileRequest useMyMcDonalds(boolean b) {
        String time = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("Z", "");
        Set<Subscription> subscriptions = new HashSet<>();
        subscriptions.add(new Subscription(time, b ? "N" : "Y", "Secondary Processing", "21"));
        subscriptions.add(new Subscription(time, b ? "Y" : "N", "Personal Marketing", "23"));
        subscriptions.add(new Subscription(time, b ? "Y" : "N", "Loyalty Enrollment", "24"));
        subscriptions.add(new Subscription(time, b ? "Y" : "N", "Loyalty Communication", "25"));
        customerInformation.setSubscriptions(subscriptions);
        return this;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/profile";
    }
}