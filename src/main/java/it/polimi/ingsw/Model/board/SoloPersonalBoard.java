package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.ActionToken;

import java.util.ArrayList;
import java.util.List;

public class SoloPersonalBoard extends PersonalBoard{

    private List<ActionToken> actionTokens;

    public SoloPersonalBoard(ArrayList<ActionToken> actionTokens) {
        super();
        this.actionTokens = new ArrayList<>();
        this.actionTokens = actionTokens;
    }
}
