package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.card.DevelopmentCardTable;
import it.polimi.ingsw.Model.card.LeaderCardDeck;
import it.polimi.ingsw.Model.market.MarketStructure;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/*
 * SOFI*/

/**
 * this class has the same function as the Board Manager but manage the Solo Game, so has the same method exept
 * a new one for take the Action Token (only Solo Game)
 */

public class SoloBoardManager extends BoardManager{

    private ArrayList<ActionToken> actionTokenDeck;

    /* constructor */
    public SoloBoardManager(Map<Integer, Player> players, MarketStructure marketStructure, DevelopmentCardTable developmentCardTable, LeaderCardDeck leaderCardDeck, ArrayList<ActionToken> actionTokenDeck) {
        super(players, marketStructure, developmentCardTable, leaderCardDeck);
        this.actionTokenDeck = actionTokenDeck;
    }

    /**
     * this method get one Action Token random, at the end of the player turn
     * these cards are all stored in a Specific Deck
     */
    public void getActionTokenDeck() {
        Random random = new Random();
        ActionToken actionToken = this.actionTokenDeck.get(random.nextInt(this.actionTokenDeck.size()));
        actionToken.activeActionToken();
    }
}
