package icu.jnet.mcd.api.response;

import java.util.Map;

public class FlurryUpResponse extends Response {

    private Map<String, String> errors;
    private String success;

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean wasSuccess() {
        return success != null && success.equals("ok");
    }
}
