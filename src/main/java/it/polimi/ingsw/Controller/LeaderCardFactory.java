package it.polimi.ingsw.Controller;


import com.google.gson.Gson;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;
import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.cardAbility.Discount;
import it.polimi.ingsw.Model.cardAbility.SpecialAbility;
import it.polimi.ingsw.Model.cardAbility.SpecialDepot;
import it.polimi.ingsw.Model.cardAbility.TransformWhiteMarble;

import java.util.ArrayList;

public class LeaderCardFactory {
    public static void main(String[] args) {
        Gson gson = new Gson();

        /* from java class to Json */

        ArrayList<Object> requirements = new ArrayList<>();
        requirements.add(new Resource(Color.BLUE));
        LeaderCard card = new LeaderCard(3,new SpecialDepot(new Resource(Color.PURPLE)),requirements);
        String json = gson.toJson(card);
        System.out.println(json);

        /* from json to create Java class
        String json2 = "{\"specialAbility\".\"AddictionalPower\"}";
        LeaderCard card2 = gson.fromJson(json2, LeaderCard.class);
        */
    }
}
