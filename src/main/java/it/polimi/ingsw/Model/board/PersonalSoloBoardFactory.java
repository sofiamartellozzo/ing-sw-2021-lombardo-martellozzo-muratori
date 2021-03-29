package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.ActionToken;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.actionAbility.CardActionAbility;
import it.polimi.ingsw.Model.actionAbility.PlusTwoBlackCrossActionAbility;
import it.polimi.ingsw.Model.actionAbility.PlusUneAndShuffleActionAbility;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;

import java.util.ArrayList;

public class PersonalSoloBoardFactory extends PersonalBoardFactory{

    public SoloPersonalBoard createGame(){
        ArrayList<CardSpace> allCardSpace = super.createGame().getCardSpaces();
        StrongBox strongBox = super.createGame().getStrongbox();
        WarehouseStandard warehouseStandard = super.createGame().getWarehouse();
        FaithTrack faithTrack = super.createGame().getFaithTrack();
        ArrayList<ActionToken> actionTokens = createActionToken();
        return new SoloPersonalBoard(faithTrack, warehouseStandard, strongBox, allCardSpace, actionTokens);
    }


    private ArrayList<ActionToken> createActionToken(){
        //where I have to take all these cards an create the dek to put in the Solo Board
        ActionToken actionToken1 = new ActionToken(new CardActionAbility(Color.PURPLE));
        ActionToken actionToken2 = new ActionToken(new CardActionAbility(Color.YELLOW));
        ActionToken actionToken3 = new ActionToken(new CardActionAbility(Color.GREEN));
        ActionToken actionToken4 = new ActionToken(new CardActionAbility(Color.BLUE));
        ActionToken actionToken5 = new ActionToken(new PlusTwoBlackCrossActionAbility());
        ActionToken actionToken6 = new ActionToken(new PlusTwoBlackCrossActionAbility());
        ActionToken actionToken7 = new ActionToken(new PlusUneAndShuffleActionAbility());
        ArrayList<ActionToken> list = new ArrayList<>();
        list.add(actionToken1);
        list.add(actionToken2);
        list.add(actionToken3);
        list.add(actionToken4);
        list.add(actionToken5);
        list.add(actionToken6);
        list.add(actionToken7);
        return list;
    }
}
