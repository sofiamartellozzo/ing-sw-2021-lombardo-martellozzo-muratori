package it.polimi.ingsw.Model.board;


import it.polimi.ingsw.Model.board.resourcemanagement.StrongBox;
import it.polimi.ingsw.Model.board.resourcemanagement.WarehouseStandard;

/*
* GIANLUCA
* */

public class PersonalBoard {
    // These attributes refers to the components of the Personal Board
    private FaithTrack faithTrack;
    private WarehouseStandard warehouse;
    private StrongBox strongbox;
    private CardSpace[] cardSpaces;

    public PersonalBoard(FaithTrack faithTrack, WarehouseStandard warehouse, StrongBox strongbox, CardSpace[] cardSpaces) {
        this.faithTrack = faithTrack;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
        this.cardSpaces = cardSpaces;
    }

    // This method refers to the Basic Production Power which is
    // equipped to every Personal Board
    public void invokeProductionPower(){};
}
