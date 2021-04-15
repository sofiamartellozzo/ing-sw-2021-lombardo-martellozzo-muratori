package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.actionAbility.ActionAbility;

/*
 * SOFI*/

/**
 * class of cards used in individual mode game
 * them are stored in the Solo Personal Bord
 * (=the board specific for the single player)
 */

public class ActionToken {

    /* the card have an ability that is activated when draw this card */
    private ActionAbility ability;

    /* constructor */
    public ActionToken(ActionAbility ability) {
        this.ability = ability;
    }

    /**
     * method called when the card is draw, so his ability is activated
     * @param boardManager, for have access to the game space
     * @param player that has draw the card
     * @throws InvalidActionException
     */
    public void activeActionToken(BoardManager boardManager, SoloPlayer player) throws InvalidActionException {
        this.ability.activeAbility(boardManager, player);
    }

    @Override
    public String toString() {
        return "ActionToken{" +
                "ability=" + ability +
                '}';
    }
}
