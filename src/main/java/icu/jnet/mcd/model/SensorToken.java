package icu.jnet.mcd.model;

public class SensorToken extends StateChangeable {

    private String token = "";

    public void setToken(String token) {
        this.token = token;
        super.notifyListeners(this);
    }

    public String getToken() {
        return token;
    }
}
