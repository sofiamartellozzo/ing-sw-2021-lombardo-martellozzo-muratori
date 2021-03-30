package it.polimi.ingsw.Model.board;


import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;
import it.polimi.ingsw.Model.card.DevelopmentCard;

import java.util.ArrayList;

/*
* SOFI*/

public class PersonalBoard {
    // These attributes refers to the components of the Personal Board
    private FaithTrack faithTrack;
    private WarehouseStandard warehouse;
    private StrongBox strongbox;
    private ArrayList<CardSpace> cardSpaces;

    public PersonalBoard(FaithTrack faithTrack, WarehouseStandard warehouse, StrongBox strongbox, ArrayList<CardSpace> cardSpaces) {
        this.faithTrack = faithTrack;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
        this.cardSpaces = cardSpaces;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public WarehouseStandard getWarehouse() {
        return warehouse;
    }

    public StrongBox getStrongbox() {
        return strongbox;
    }

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
