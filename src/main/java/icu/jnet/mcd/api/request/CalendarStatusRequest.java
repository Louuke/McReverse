package icu.jnet.mcd.api.request;

public class CalendarStatusRequest extends CalendarRequest {

    public CalendarStatusRequest(String token, String userName, String userId, String prizeId) {
        super(token, userName, userId, prizeId);
    }

    public CalendarStatusRequest(String token, String userName, String userId) {
        super(token, userName, userId);
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/appcalendar/status";
    }
}
