package it.polimi.ingsw.Model.board;


import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;
import it.polimi.ingsw.Model.card.DevelopmentCard;

import java.util.ArrayList;

/*
* SOFI*/

public class PersonalBoard {
    // These attributes refers to the components of the Personal Board
    private FaithTrack faithTrack;
    private ResourceManager resourceManager;
    //private WarehouseStandard warehouse;
    //private StrongBox strongbox;
    private ArrayList<CardSpace> cardSpaces;

    public PersonalBoard(FaithTrack faithTrack, ResourceManager resourceManager, ArrayList<CardSpace> cardSpaces) {
        this.faithTrack = faithTrack;
        this.resourceManager = resourceManager;
        //this.warehouse = warehouse;
        //this.strongbox = strongbox;
        this.cardSpaces = cardSpaces;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public WarehouseStandard getWarehouse() {
        return resourceManager.getWarehouse();
    }

    public StrongBox getStrongbox() {
        return resourceManager.getStrongBox();
    }

    /**
     *  class wich has all the resources, so both the Strong Box and the warehouse
     */
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    /**
     * method invocated to get all the victory points from the 3 card space
     * @return sum of victory points
     */
    public int getVictoryPointsFromCardSpaces(){
        int points = 0;
        points += this.cardSpaces.get(0).getTotVictoryPoints();
        points += this.cardSpaces.get(1).getTotVictoryPoints();
        points += this.cardSpaces.get(2).getTotVictoryPoints();
        return points;
    }

    /**
     * this method is used to get a particular Card Space
     * the number i
     * @param i
     * @return
     * @throws IndexOutOfBoundsException
     */
    public CardSpace getCardSpace(int i) throws IndexOutOfBoundsException{
        if (i>2){
            throw new IndexOutOfBoundsException("There are maximum 3 card spaces! Not more");
        }
        else{
            return this.cardSpaces.get(i);
        }
    }

    /**
     * method used to get all the (3) card space
     * @return
     */
    public ArrayList<CardSpace> getCardSpaces() {
        return cardSpaces;
    }

    // This method refers to the Basic Production Power which is
    // equipped to every Personal Board
    public void invokeProductionPowerFromStrongBox(DevelopmentCard card){
        //Dev Card Input needesd
        //card.useProductionPower();
    }
}
