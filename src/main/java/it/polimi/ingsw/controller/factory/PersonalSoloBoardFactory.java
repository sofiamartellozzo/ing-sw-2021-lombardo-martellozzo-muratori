package it.polimi.ingsw.controller.factory;

import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.actionAbility.CardActionAbility;
import it.polimi.ingsw.model.actionAbility.PlusTwoBlackCrossActionAbility;
import it.polimi.ingsw.model.actionAbility.PlusUneAndShuffleActionAbility;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;

import java.util.ArrayList;

/**
 * Pattern: FACTORY
 * this class is made to create the personal board in the Single Player Game, so it's the same of the normal Personal Board
 * the only difference is that it creates the Deck of action tokens that will be used at the end of each turn
 */

public class PersonalSoloBoardFactory extends PersonalBoardFactory {


    /**
     * creates all the components of the game ant putting them together in a SOLO personal board
     * @return -> SOLO personal board
     */
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


    /**
     * creates a list of action token used only in the single player game
     * @return -> list of Action Token
     */
    private ArrayList<ActionToken> createActionToken(){
        //where I have to take all these cards an create the deck to put in the Solo Board
        ActionToken actionToken1 = new ActionToken(new CardActionAbility(Color.PURPLE), 1);
        ActionToken actionToken2 = new ActionToken(new CardActionAbility(Color.YELLOW), 2);
        ActionToken actionToken3 = new ActionToken(new CardActionAbility(Color.GREEN), 3);
        ActionToken actionToken4 = new ActionToken(new CardActionAbility(Color.BLUE), 4);
        ActionToken actionToken5 = new ActionToken(new PlusTwoBlackCrossActionAbility(), 5);
        ActionToken actionToken6 = new ActionToken(new PlusTwoBlackCrossActionAbility(), 6);
        ActionToken actionToken7 = new ActionToken(new PlusUneAndShuffleActionAbility(), 7);
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