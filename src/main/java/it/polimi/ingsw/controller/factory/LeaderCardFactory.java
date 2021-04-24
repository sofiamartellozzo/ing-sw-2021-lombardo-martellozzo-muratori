package it.polimi.ingsw.controller.factory;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.cardAbility.SpecialAbility;
import it.polimi.ingsw.model.cardAbility.SpecialDepot;
import it.polimi.ingsw.utility.SmallDevelopCard;
import netscape.javascript.JSObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LeaderCardFactory {
    public static void main(String[] args) throws FileNotFoundException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        /* from java class to Json */

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(new Resource(Color.BLUE));
        ArrayList<Object> requirements = new ArrayList<>();
        //requirements.add(new DevelopmentCard(1, Color.GREEN, 2, cost, cost, cost ));
        requirements.add(new SmallDevelopCard(Color.GREEN, 0));
        requirements.add(new SmallDevelopCard(Color.BLUE, 1));
        LeaderCard card = new LeaderCard(3,2,new SpecialDepot(new Resource(Color.PURPLE)),requirements);
        String json = gson.toJson(card);
        System.out.println(json);
        /* from json to create Java class*/

        //BufferedReader reader = new BufferedReader(new FileReader("src/main/java/it/polimi/ingsw/utility/leaderCard.json"));
        //Type list = new TypeToken<ArrayList<LeaderCard>>(){}.getType();
        //ArrayList<LeaderCard> cards = gson.fromJson(reader, list);
        //LeaderCard card = new LeaderCard(Integer.valueOf(jsonObject.get("cardID").toString()) ,Integer.valueOf(jsonObject.get("victoryPoints").toString()), (SpecialAbility) jsonObject.get("specialAbility"),(ArrayList<Object>) jsonObject.get("requirements"));
        //System.out.println(jsonObject);
        /*
        try {
            FileWriter file = new FileWriter("leaderCard.json");
            file.write(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String json2 = */
       // LeaderCard card2 = gson.fromJson(json2, LeaderCard.class);

    }

}
