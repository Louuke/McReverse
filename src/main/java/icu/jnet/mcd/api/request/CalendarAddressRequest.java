package icu.jnet.mcd.api.request;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.UrlEncodedContent;

import java.util.Map;

public class CalendarAddressRequest implements Request {

    private final Map<String, String> form;

    public CalendarAddressRequest(Map<String, String> form) {
        this.form = form;
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/appcalendar/uploadAddress";
    }

    @Override
    public HttpContent getContent() {
        return new UrlEncodedContent(form);
    }
}
