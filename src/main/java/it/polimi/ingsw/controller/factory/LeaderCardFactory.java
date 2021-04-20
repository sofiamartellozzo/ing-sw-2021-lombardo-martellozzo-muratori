package it.polimi.ingsw.controller.factory;


import com.google.gson.Gson;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.cardAbility.SpecialDepot;

import java.util.ArrayList;

public class LeaderCardFactory {
    public static void main(String[] args) {
        Gson gson = new Gson();

        /* from java class to Json */

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(new Resource(Color.BLUE));
        ArrayList<Object> requirements = new ArrayList<>();
        requirements.add(new DevelopmentCard(1, Color.GREEN, 2, cost, cost, cost ));
        LeaderCard card = new LeaderCard(3,new SpecialDepot(new Resource(Color.PURPLE)),requirements);
        String json = gson.toJson(card);
        System.out.println(json);

        /* from json to create Java class
        String json2 = "{\"specialAbility\".\"AddictionalPower\"}";
        LeaderCard card2 = gson.fromJson(json2, LeaderCard.class);
        */
    }
}
