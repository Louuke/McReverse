package org.jannsen.mcreverse.api.entity.profile;

import java.util.ArrayList;
import java.util.List;

public class CustomerInformation {

    private final Object[] phone = new Object[0];
    private final Base base = new Base();
    private final Audit audit = new Audit();
    private String hashedDcsId, languageCode, marketCode;
    private Devices[] devices;
    private Address[] address;
    private List<Subscription> subscriptions;

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

    public Devices[] getDevices() {
        return devices;
    }

    public Address[] getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = new Address[] {address};
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions != null ? subscriptions : new ArrayList<>();
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
