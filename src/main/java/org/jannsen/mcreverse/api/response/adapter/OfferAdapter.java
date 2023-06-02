package org.jannsen.mcreverse.api.response.adapter;

import org.jannsen.mcreverse.api.entity.offer.Offer;
import org.jannsen.mcreverse.api.McClientSettings;

import java.util.regex.Pattern;

import static org.jannsen.mcreverse.utils.Utils.timeToUnix;
import static org.jannsen.mcreverse.utils.ObjectModification.setField;

public class OfferAdapter extends AbstractAdapterFactory {

    private static final Pattern pricePattern = Pattern.compile("\\d+[,|.]\\d\\d");
    private static final Pattern clockPattern = Pattern.compile("\\d\\d-\\d\\d");

    public OfferAdapter() {
        super(Offer.class);
    }

    @Override
    public void modifyPojo(Object pojo) {
        Offer offer = (Offer) pojo;
        setField(offer, "shortName", getName(offer));
        setField(offer, "price", getPrice(offer));
        setField(offer, "priceCents", pricePattern.matcher(offer.getPrice()).results()
                .map(result -> result.group().replaceAll("[,|.]", ""))
                .map(Integer::parseInt).findAny().orElse(0));
        setField(offer, "validFromUnix", timeToUnix(offer.getLocalValidFrom(), McClientSettings.ZONE_ID));
        setField(offer, "validToUnix", timeToUnix(offer.getLocalValidTo(), McClientSettings.ZONE_ID));
        setField(offer, "availableHourFrom", clockPattern.matcher(offer.getPrice()).results()
                .map(result -> Integer.parseInt(result.group().split("-")[0])).findAny().orElse(0));
        setField(offer, "availableHourTo", clockPattern.matcher(offer.getPrice()).results()
                .map(result -> Integer.parseInt(result.group().split("-")[1])).findAny().orElse(24));
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
