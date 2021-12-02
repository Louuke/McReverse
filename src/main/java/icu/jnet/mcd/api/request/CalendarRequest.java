package icu.jnet.mcd.api.request;

public class CalendarRequest {

    private final String formId = "christmas2102", token, userName, userId;
    private String prizeId;

    public CalendarRequest(String token, String userName, String userId, String prizeId) {
        this(token, userName, userId);
        this.prizeId = prizeId;
    }

    public CalendarRequest(String token, String userName, String userId) {
        this.token = token;
        this.userName = userName;
        this.userId = userId;
    }
}
