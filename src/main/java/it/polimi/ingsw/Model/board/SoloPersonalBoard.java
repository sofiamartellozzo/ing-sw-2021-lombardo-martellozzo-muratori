package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.ActionToken;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;

import java.util.ArrayList;
import java.util.List;

public class SoloPersonalBoard extends PersonalBoard{

    private List<ActionToken> actionTokens;

    public SoloPersonalBoard(FaithTrack faithTrack, WarehouseStandard warehouseStandard, StrongBox strongBox, ArrayList<CardSpace> cardSpaces, ArrayList<ActionToken> actionTokens) {
        super(faithTrack, warehouseStandard, strongBox, cardSpaces);
        this.actionTokens = new ArrayList<>();
        this.actionTokens = actionTokens;
    }
}
