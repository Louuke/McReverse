package icu.jnet.mcd.api.response;

import java.util.HashMap;
import java.util.List;

public class CalendarStateResponse {

    private HashMap<String, String> errors;
    private List<Object> instantPrizes;
    private String success, userId;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public List<Object> getInstantPrizes() {
        return instantPrizes;
    }

    public boolean success() {
        return success != null && success.equals("ok");
    }

    public String getUserId() {
        return userId;
    }
}
