package it.polimi.ingsw.model.actionAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.SoloPlayer;

import java.io.Serializable;

/**
 * (In the solo Player)
 * An action ability that removes two cards of a specific color from the Dev Card Table
 */

public class CardActionAbility implements ActionAbility, Serializable {
    private Color color;

    /**
     * constructor of the class with the color of the cards that will be removed
     * @param color
     */
    public CardActionAbility(Color color) {
        this.color = color;
    }

    /**
     * method called when is taken an Action Token with this ability
     */
    public void activeAbility(BoardManager boardManager, SoloPlayer player) throws InvalidActionException {
        /* remove two card of this.color if the deck is not empty */
        boardManager.getDevelopmentCardTable().removeCardByColor(this.color);
        if(!boardManager.checkEmptyColumn()){
            boardManager.getDevelopmentCardTable().removeCardByColor(this.color);
        }

    }

    @Override
    public String toString() {
        return "   Ability: "  + "removed Two card of color " + color.toString();
    }


}
