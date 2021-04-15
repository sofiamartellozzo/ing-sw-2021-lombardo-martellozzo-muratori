package it.polimi.ingsw.model.board;


import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

/*
* SOFI*/

/**
 * This class contains all the components of the Board of a single player
 * so each player has his own
 */

public class PersonalBoard implements PersonalBoardInterface {
    /* These attributes refers to the components of the Personal Board */
    private FaithTrack faithTrack;
    private ResourceManager resourceManager;  //contains both SrongBox and Wharehouse
    private ArrayList<CardSpace> cardSpaces;  //3 for each Board

    public PersonalBoard(FaithTrack faithTrack, ResourceManager resourceManager, ArrayList<CardSpace> cardSpaces) {
        this.faithTrack = faithTrack;
        this.resourceManager = resourceManager;
        this.cardSpaces = cardSpaces;
    }

    @Override
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    @Override
    public Warehouse getWarehouse() {
        return resourceManager.getWarehouse();
    }

    @Override
    public StrongBox getStrongbox() {
        return resourceManager.getStrongBox();
    }

    /**
     *  class wich has all the resources, so both the Strong Box and the warehouse
     *  ad used to manage moving the resources inside them
     */
    @Override
    public ResourceManager getResourceManager() {
        return resourceManager;
    }


    /**
     * method used to get all the (3) card space
     * @return
     */
    @Override
    public ArrayList<CardSpace> getCardSpaces() {
        return cardSpaces;
    }


    /**
     * this method is used to get a particular Card Space
     * the number i
     * @param i
     * @return card Space in position i :[0, 1, 2]
     * @throws IndexOutOfBoundsException if input is not valid
     */
    @Override
    public CardSpace getCardSpace(int i) throws IndexOutOfBoundsException{
        if ((i>2)||(i<0)){
            throw new IndexOutOfBoundsException("There are maximum 3 card spaces! Not more");
        }
        else{
            return this.cardSpaces.get(i);
        }
    }

    /**
     * this one gets all Cards contained in the 3 card spaces of the personal board
     * @return all Development Card in this specific Personal Bord
     */
    @Override
    public ArrayList<DevelopmentCard> getAllCards(){
        ArrayList<DevelopmentCard> allCards = new ArrayList<>();
        for (CardSpace space: getCardSpaces()){
            allCards.addAll(space.getCards());
        }
        return allCards;
    }

    /**
     * this one gets all Development Card in a specific Space Card (one of the 3)
     * @param i
     * @return all cards of the space(i)
     */
    @Override
    public ArrayList<DevelopmentCard> getAllCardOfOneSpace(int i){
        CardSpace cardSpaceSpecific = getCardSpace(i);
        ArrayList<DevelopmentCard> allCards = new ArrayList<>();
        for (DevelopmentCard card: cardSpaceSpecific.getCards()){
            allCards.add(card);
        }
        return allCards;
    }


    // This method refers to the Basic Production Power which is
    // equipped to every Personal Board
    public void invokeProductionPowerFromStrongBox(DevelopmentCard card){
        //Dev Card Input needed
        //card.useProductionPower();
    }

    /**
     * method invocated to get all the victory points from the 3 card space
     * @return sum of victory points
     */
    @Override
    public int getVictoryPointsFromCardSpaces(){
        int points = 0;
        for (CardSpace cardSpace: getCardSpaces()){
            points += cardSpace.getTotVictoryPoints();
        }
        return points;
    }
}
