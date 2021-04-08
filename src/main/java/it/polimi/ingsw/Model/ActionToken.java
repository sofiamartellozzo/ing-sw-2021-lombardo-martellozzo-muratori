package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.actionAbility.ActionAbility;

/*
 * SOFI*/

public class ActionToken {
    /* class of cards used in individual mode game */

    private ActionAbility ability;

    public ActionToken(ActionAbility ability) {
        this.ability = ability;
    }

    public void activeActionToken(BoardManager boardManager, SoloPlayer player) throws InvalidActionException {
        this.ability.activeAbility(boardManager, player);
    }
}
