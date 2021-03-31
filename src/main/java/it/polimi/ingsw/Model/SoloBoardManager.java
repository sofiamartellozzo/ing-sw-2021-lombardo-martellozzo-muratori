package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.card.DevelopmentCardTable;
import it.polimi.ingsw.Model.card.LeaderCardDeck;
import it.polimi.ingsw.Model.market.MarketStructure;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/*
 * SOFI*/

public class SoloBoardManager extends BoardManager{

    private ArrayList<ActionToken> actionTokenDeck;

    public SoloBoardManager(Map<Integer, Player> players, MarketStructure marketStructure, DevelopmentCardTable developmentCardTable, LeaderCardDeck leaderCardDeck, ArrayList<ActionToken> actionTokenDeck) {
        super(players, marketStructure, developmentCardTable, leaderCardDeck);
        this.actionTokenDeck = actionTokenDeck;
    }

    public void getActionTokenDeck() {
        Random random = new Random();
        ActionToken actionToken = this.actionTokenDeck.get(random.nextInt(this.actionTokenDeck.size()));
        actionToken.activeActionToken();
    }
}
