package org.jannsen.mcreverse.api.entity.profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerInformation {

    private final Object[] phone = new Object[0];
    private final Base base = new Base();
    private final Audit audit = new Audit();
    private String hashedDcsId, languageCode, marketCode;
    private Device[] devices;
    private Address[] address;
    private Set<Subscription> subscriptions;

    public String getHashedDcsId() {
        return hashedDcsId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public Base getBase() {
        return base;
    }

    public String getZipCode() {
        return address != null ? address[0].getDetails().get(0).getAddressLineDetails().getZipCode() : "00000";
    }

    public Device[] getDevices() {
        return devices;
    }

    public Address[] getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = new Address[] {address};
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions != null ? subscriptions : new HashSet<>();
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
