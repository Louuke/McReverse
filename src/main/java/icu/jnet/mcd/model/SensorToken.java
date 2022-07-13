package icu.jnet.mcd.model;

public class SensorToken extends StateChangeable {

    private String token;
    private boolean valid = true;

    public void setSensorToken(String token) {
        this.token = token;
        this.valid = true;
        super.notifyListeners(this);
    }

    public void setExpired() {
        this.valid = false;
        super.notifyListeners(this);
    }

    public String getToken() {
        return token;
    }

    public boolean isValid() {
        return valid;
    }
}
