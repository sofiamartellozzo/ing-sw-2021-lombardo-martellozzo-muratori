package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.actionAbility.ActionAbility;

public class ActionToken {
    /* class of cards used in individual mode game */

    private ActionAbility ability;

    public ActionToken(ActionAbility ability) {
        this.ability = ability;
    }
}
