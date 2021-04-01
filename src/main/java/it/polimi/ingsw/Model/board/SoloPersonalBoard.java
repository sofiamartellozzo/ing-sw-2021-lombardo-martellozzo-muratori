package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.ActionToken;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;

import java.util.ArrayList;
import java.util.List;

/*
* SOFI*/
/**
 * this class is the same as the Personal Board but for the Solo Game
 */
public class SoloPersonalBoard extends PersonalBoard{

    private List<ActionToken> actionTokens;

    public SoloPersonalBoard(FaithTrack faithTrack, ResourceManager resourceManager, ArrayList<CardSpace> cardSpaces, ArrayList<ActionToken> actionTokens) {
        super(faithTrack, resourceManager, cardSpaces);
        this.actionTokens = new ArrayList<>();
        this.actionTokens = actionTokens;
    }
}
