package it.polimi.ingsw.Controller;


import com.google.gson.Gson;
import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.cardAbility.Discount;
import it.polimi.ingsw.Model.cardAbility.SpecialAbility;
import it.polimi.ingsw.Model.cardAbility.TransformWhiteMarble;

public class LeaderCardFactory {
    public static void main(String[] args) {
        Gson gson = new Gson();

        /* from java class to Json*/
        LeaderCard card = new LeaderCard(1);
        String json = gson.toJson(card);

        /*
        /* from json to create Java class
        String json2 = "{\"specialAbility\".\"AddictionalPower\"}";
        LeaderCard card2 = gson.fromJson(json2, LeaderCard.class);
        */
    }
}
