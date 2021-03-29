package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.ActionToken;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;

import java.util.ArrayList;

/*
* GIANLUCA
* PATTERN
* */
public class PersonalBoardFactory {
    /*
    * In order to create the Personal Board of each Player
    */
    public PersonalBoard createGame(){
        ArrayList<CardSpace> allCardSpace = createCardSpaces();
        StrongBox strongBox = new StrongBox();
        WarehouseStandard warehouseStandard = new WarehouseStandard();
        FaithTrack faithTrack = new FaithTrack();
        return new PersonalBoard(faithTrack, warehouseStandard, strongBox, allCardSpace);
    }

    public ArrayList<CardSpace> createCardSpaces(){
        CardSpace cardSpace1 = new CardSpace(1);
        CardSpace cardSpace2 = new CardSpace(2);
        CardSpace cardSpace3 = new CardSpace(3);
        ArrayList<CardSpace> allCardSpace = new ArrayList<>();
        allCardSpace.add(cardSpace1);
        allCardSpace.add(cardSpace2);
        allCardSpace.add(cardSpace3);
        return allCardSpace;
    }
}
