package org.jannsen.mcreverse.api.response.adapter;

import org.jannsen.mcreverse.api.entity.offer.Offer;
import org.jannsen.mcreverse.api.McClientSettings;

import java.lang.reflect.Field;

import static org.jannsen.mcreverse.utils.Utils.timeToUnix;

public class OfferAdapter extends AbstractAdapterFactory {

    public OfferAdapter() {
        super(Offer.class);
    }

    @Override
    public void modifyPojo(Object pojo) {
        setPriceAndName((Offer) pojo);
        setValidUnixTime((Offer) pojo);
    }

    private void setPriceAndName(Offer offer) {
        try {
            Field name = Offer.class.getDeclaredField("shortName");
            Field price = Offer.class.getDeclaredField("price");
            name.setAccessible(true);
            price.setAccessible(true);
            name.set(offer, getName(offer));
            price.set(offer, getPrice(offer));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValidUnixTime(Offer offer) {
        try {
            Field validFromUnix = Offer.class.getDeclaredField("validFromUnix");
            Field validToUnix = Offer.class.getDeclaredField("validToUnix");
            validFromUnix.setAccessible(true);
            validToUnix.setAccessible(true);
            validFromUnix.set(offer, timeToUnix(offer.getLocalValidFrom(), McClientSettings.ZONE_ID));
            validToUnix.set(offer, timeToUnix(offer.getLocalValidTo(), McClientSettings.ZONE_ID));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String getName(Offer offer) {
        String fullName = offer.getFullName();
        return fullName.contains("\n") ? fullName.split("\n")[0].strip() : fullName;
    }

    private String getPrice(Offer offer) {
        String fullName = offer.getFullName();
        return fullName.contains("\n") ? fullName.split("\n")[1].strip() : "0";
    }
}
