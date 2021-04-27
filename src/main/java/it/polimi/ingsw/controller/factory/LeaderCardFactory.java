package it.polimi.ingsw.controller.factory;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.cardAbility.SpecialAbility;
import it.polimi.ingsw.model.cardAbility.SpecialDepot;
import it.polimi.ingsw.model.cardAbility.TypeAbility;
import it.polimi.ingsw.utility.SmallDevelopCard;
import it.polimi.ingsw.utility.SmallLeaderCard;
import netscape.javascript.JSObject;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;

public class LeaderCardFactory {
    public static void main(String[] args) throws FileNotFoundException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        /* from java class to Json */
        /*
        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(new Resource(Color.BLUE));
        ArrayList<Object> requirements = new ArrayList<>();
        //requirements.add(new DevelopmentCard(1, Color.GREEN, 2, cost, cost, cost ));
        requirements.add(new SmallDevelopCard(Color.GREEN, 0));
        requirements.add(new SmallDevelopCard(Color.BLUE, 1));
        SmallLeaderCard leaderCard = new SmallLeaderCard(TypeResource.COIN, requirements, 1,TypeAbility.ADDITIONAL_POWER, 2);
        String json = gson.toJson(leaderCard);
        System.out.println(json);*/


        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/it/polimi/ingsw/utility/leaderCard.json"));
        Type list = new TypeToken<ArrayList<SmallLeaderCard>>() {
        }.getType();
        ArrayList<SmallLeaderCard> cards = gson.fromJson(reader, list);
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        for (SmallLeaderCard smallCard: cards) {
            leaderCards.add(new LeaderCard(smallCard.getCardID(),smallCard.getVictoryPoints(),smallCard.getTypeAbility(),smallCard.getSpecialResource(), smallCard.getRequirements()));
        }
    }

}
