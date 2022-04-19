package icu.jnet.mcd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import icu.jnet.mcd.api.McClient;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        McClient client = new McClient();
        client.login("robertkholder+andeee@gmail.com", "123456aA!", "91y5ji1lovu3qi2r");

        System.out.println(client.getPoints().getTotalPoints());

        //System.out.println(gson.toJson(client.participateRaffle()));
        //System.out.println(gson.toJson(client.useMyMcDonalds(true)));
        //System.out.println(gson.toJson(client.getProfile()));
    }
}
