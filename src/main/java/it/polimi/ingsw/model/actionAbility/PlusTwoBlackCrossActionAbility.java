package it.polimi.ingsw.model.actionAbility;

import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.SoloPlayer;

import java.io.Serializable;

/**
 * (In the solo Player)
 * An Action Ability that increases the position of Lorenzo's Faith Marker of +2 positions
 */

public class PlusTwoBlackCrossActionAbility implements ActionAbility, Serializable {

    /**
     * method called when is taken an Action Token with this ability
     */
    @Override
    public void activeAbility(BoardManager boardManager, SoloPlayer player) {
        player.getGameSpace().increaseLorenzoIlMagnifico();
        player.getGameSpace().increaseLorenzoIlMagnifico();
    }

    @Override
    public String toString() {
        return "PlusTwo";
    }
}
