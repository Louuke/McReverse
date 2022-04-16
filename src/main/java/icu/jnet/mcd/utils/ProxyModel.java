package icu.jnet.mcd.utils;

public class ProxyModel {

    private final String host;
    private final int port;
    private String user, password;


    public ProxyModel(String host, int port, String user, String password) {
        this(host, port);
        this.user = user;
        this.password = password;
    }

    public ProxyModel(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }
}
