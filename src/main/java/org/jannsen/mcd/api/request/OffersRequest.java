package org.jannsen.mcd.api.request;

public class OffersRequest extends Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/offers?distance=80&latitude=53.557&longitude=10.006&optOuts=&timezoneOffsetInMinutes=120";
    }
}
