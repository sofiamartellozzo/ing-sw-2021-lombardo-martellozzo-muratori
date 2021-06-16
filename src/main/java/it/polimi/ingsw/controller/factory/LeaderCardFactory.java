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

/**
 * Pattern: FACTORY
 * the purpose of this class is to create the Leader Card Deck for a single game
 * it uses a file json and while reading it, creates all the 16 cards with a specific Special Ability each one
 */
public class LeaderCardFactory {

    /**
     * creates the Leader Cards using a .json file
     * @return the deck of Leader Cards
     * @throws FileNotFoundException
     */
    public ArrayList<LeaderCard> createLeaderCardDeck() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        //BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/leaderCard.json"));
        Type list = new TypeToken<ArrayList<SmallLeaderCard>>() {
        }.getType();
        ArrayList<SmallLeaderCard> cards = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/leaderCard.json")), list);
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        for (SmallLeaderCard smallCard: cards) {
            leaderCards.add(new LeaderCard(smallCard.getCardID(),smallCard.getVictoryPoints(),smallCard.getTypeAbility(),smallCard.getSpecialResource(), smallCard.getRequirements()));
        }
        return leaderCards;
    }

}
