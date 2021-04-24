package it.polimi.ingsw.controller.factory;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.cardAbility.SpecialDepot;
import it.polimi.ingsw.model.cardAbility.TypeAbility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LeaderCardFactory {
    public static void main(String[] args) throws FileNotFoundException {

        //GsonBuilder builder = new GsonBuilder();
        //Gson gson = builder.create();
        Gson gson = new Gson();


        /* from java class to Json */

        /*ArrayList<Resource> cost = new ArrayList<>();
        cost.add(new Resource(Color.BLUE));
        ArrayList<Object> requirements = new ArrayList<>();
        requirements.add(0,new Resource(Color.PURPLE));
        LeaderCard card = new LeaderCard(2,2, TypeAbility.SPECIAL_DEPOT, TypeResource.COIN,requirements);
        String json = gson.toJson(card);
        System.out.println(json);*/

        /* from json to create Java class */
      BufferedReader reader = new BufferedReader(new FileReader("src/main/java/it/polimi/ingsw/leaderCard.json"));
      Type list = new TypeToken<ArrayList<LeaderCard>>(){}.getType();
      ArrayList<LeaderCard> cards = gson.fromJson(reader,list);

    }
}
