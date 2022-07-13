package icu.jnet.mcd;

import com.google.gson.GsonBuilder;
import icu.jnet.mcd.api.McClient;

public class Main {

    public static void main(String[] args) {
        McClient client = new McClient("d");
        System.out.println(client.login("miller@mailx.cyou", "123456aA!"));
        while(true) {
            System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(client.getProfile()));
        }
    }
}
