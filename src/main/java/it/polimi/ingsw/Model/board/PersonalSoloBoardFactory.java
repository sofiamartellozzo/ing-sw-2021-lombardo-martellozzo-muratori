package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.ActionToken;
import it.polimi.ingsw.Model.actionAbility.CardActionAbility;
import it.polimi.ingsw.Model.board.resourcemanagement.ResourceManagement;
import it.polimi.ingsw.Model.board.resourcemanagement.StrongBox;
import it.polimi.ingsw.Model.board.resourcemanagement.WarehouseStandard;

import java.util.ArrayList;
import java.util.List;

public class PersonalSoloBoardFactory {
    public SoloPersonalBoard createGame(){
        //CardSpace cardSpace1 = new CardSpace();
        //CardSpace cardSpace2 = new CardSpace();
        //CardSpace cardSpace3 = new CardSpace();
        //List<CardSpace> allCardSpace = new ArrayList<>();
        //allCardSpace.add(new CardSpace());
        //allCardSpace.add(new CardSpace());
        //allCardSpace.add(new CardSpace());
        /*StrongBox strongBox = new StrongBox();*/
        WarehouseStandard warehouseStandard = new WarehouseStandard();
        FaithTrack faithTrack = createFaithTrack();
        return new SoloPersonalBoard(createActionToken());
    }

    private FaithTrack createFaithTrack(){
        //FaithMarker faithMarker = new FaithMarker();
        return new FaithTrack();
    }

    private ArrayList<ActionToken> createActionToken(){
        //where I have to take all these cards an create the dek to put in the Solo Board
        ActionToken at = new ActionToken(new CardActionAbility());
        ArrayList<ActionToken> list = new ArrayList<>();
        list.add(at);
        return list;
    }
}
