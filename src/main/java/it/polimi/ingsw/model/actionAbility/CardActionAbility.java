package it.polimi.ingsw.model.actionAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.SoloPlayer;

/*
 * SOFI*/

/**
 * One type of the ability that do the remove of the card
 */

public class CardActionAbility implements ActionAbility {
    private Color color;

    /* constructor whit the color of the card to revome */
    public CardActionAbility(Color color) {
        this.color = color;
    }

    /**
     * method called when take the Action Token with this ability
     */
    public void activeAbility(BoardManager boardManager, SoloPlayer player) throws InvalidActionException {
        /* remove two card of this.color */
        boardManager.getDevelopmentCardTable().getSquare(this.color);
        boardManager.getDevelopmentCardTable().getSquare(this.color);
    }
}
