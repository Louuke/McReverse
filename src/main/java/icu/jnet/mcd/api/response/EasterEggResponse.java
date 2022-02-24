package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class EasterEggResponse extends Response {

    private HashMap<String, String> errors;
    private String id, success, type;
    private int timeoutInSeconds;

    public String getId() {
        return id;
    }

    public boolean success() {
        return success != null && success.equals("ok");
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public String getType() {
        return type;
    }

    public int getTimeoutInSeconds() {
        return timeoutInSeconds;
    }
}
