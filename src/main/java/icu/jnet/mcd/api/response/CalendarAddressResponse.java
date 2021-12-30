package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class CalendarAddressResponse extends Response {

    private String date, success;
    private HashMap<String, Object> errors;

    public String getDate() {
        return date;
    }

    public String getSuccess() {
        return success;
    }

    public HashMap<String, Object> getErrors() {
        return errors;
    }
}
