package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.actionAbility.CardActionAbility;
import it.polimi.ingsw.model.actionAbility.PlusTwoBlackCrossActionAbility;
import it.polimi.ingsw.model.actionAbility.PlusUneAndShuffleActionAbility;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;

import java.util.ArrayList;

/*
 * SOFI*/
public class PersonalSoloBoardFactory extends PersonalBoardFactory{

    public SoloPersonalBoard createGame(){
        ArrayList<CardSpace> allCardSpace = createCardSpaces();
        StrongBox strongBox = new StrongBox();
        WarehouseStandard warehouseStandard = new WarehouseStandard();
        ResourceManager resourceManager = new ResourceManager(strongBox, warehouseStandard);
        FaithTrack faithTrack = createFaithTrack();
        LorenzoFaithMarker lorenzoFaithMarker = new LorenzoFaithMarker();
        SoloFaithTrack soloFaithTrack = new SoloFaithTrack(faithTrack.getPathBox(), faithTrack.getPopesFavorTiles(), faithTrack.getFaithMarker(), faithTrack.getVaticanSections(), lorenzoFaithMarker);
        ArrayList<ActionToken> actionTokens = createActionToken();
        return new SoloPersonalBoard(soloFaithTrack, resourceManager, allCardSpace, actionTokens);
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
