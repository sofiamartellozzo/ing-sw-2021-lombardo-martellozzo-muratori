package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.card.DevelopmentCardDeck;
import it.polimi.ingsw.Model.card.DevelopmentCardTable;
import it.polimi.ingsw.Model.card.LeaderCardDeck;
import it.polimi.ingsw.Model.market.MarketBoard;
import it.polimi.ingsw.Model.market.MarketStructure;

import java.util.ArrayList;
import java.util.Map;

/*
SOFI
 */

/**
 * A class containing the main info about the game, such as the list of the players, the market, the Development card dek
 * and the Leader card deck
 * to make them easy accessible from the controller package */
public class BoardManager {

    /* the common elements for all the players */
    private Map<Integer, PlayerInterface> players;
    private MarketStructure marketStructure;
    private DevelopmentCardTable developmentCardTable;
    private LeaderCardDeck leaderCardDeck;
    private ResourcesSupply resourcesSupply;

    public BoardManager(Map<Integer, PlayerInterface> players, MarketStructure marketStructure, DevelopmentCardTable developmentCardTable, LeaderCardDeck leaderCardDeck, ResourcesSupply resourcesSupply) {
        this.players = players;
        this.marketStructure = marketStructure;
        this.developmentCardTable = developmentCardTable;
        this.leaderCardDeck = leaderCardDeck;
        this.resourcesSupply = resourcesSupply;
    }

    public Map<Integer, PlayerInterface> getPlayers() {
        return players;
    }

    public MarketStructure getMarketStructure() {
        return marketStructure;
    }

    public DevelopmentCardTable getDevelopmentCardTable() {
        return developmentCardTable;
    }

    public LeaderCardDeck getLeaderCardDeck() {
        return leaderCardDeck;
    }

    public ResourcesSupply getResourcesSupply() {
        return resourcesSupply;
    }
}