package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.LeaderCardDeck;
import it.polimi.ingsw.model.market.MarketStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class containing the main info about the game, such as the list of the players, the market, the Leader card deck
 * and the table of Development cards
 * to make them easy accessible from the controller package */
public class BoardManager implements Serializable {

    /* the common elements for all the players */
    private Map<Integer, PlayerInterface> players;
    private MarketStructure marketStructure;
    private DevelopmentCardTable developmentCardTable;
    private LeaderCardDeck leaderCardDeck;
    private ResourcesSupply resourcesSupply;

    /**
     * constructor of the class
     * @param players --> list of players of the game
     * @param marketStructure --> market of te game
     * @param developmentCardTable --> table of dev cards (3x4)
     * @param leaderCardDeck --> deck of 16 cards
     * @param resourcesSupply --> containers of all the resources
     */
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



    public void setDevelopmentCardTable(DevelopmentCardTable developmentCardTable) {
        this.developmentCardTable = developmentCardTable;
    }

    /**
     * get all the id of the card contained in the Leader Card Deck
     * @return ArrayList of Integer, referred to the Leader Card Id
     */
    public ArrayList<Integer> getAllLeaderCard(){
        ArrayList<Integer> allCards = new ArrayList<>();
        for (LeaderCard card: leaderCardDeck.getCards()) {
            allCards.add(card.getCardID());
        }
        return allCards;
    }

    /**
     * Scans all the decks of the table and check if some of them are completely empty.
     * If yes the boolean matrix with the position of the deck [row][column] is set to false,
     * else true.
     * @return
     */
    public boolean[][] getAvailable(PlayerInterface player){
        return developmentCardTable.getAvailable(player);
    }



}
