package it.polimi.ingsw.model.actionAbility;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.SoloPlayer;

import java.io.Serializable;

/**
 * (In the solo Player)
 * An Action Ability that increases the position of Lorenzo's Faith Marker of +1 position and shuffle the action token's deck
 */

public class PlusUneAndShuffleActionAbility implements ActionAbility, Serializable {
    /**
     * method called when is taken an Action Token with this ability
     */
    @Override
    public void activeAbility(BoardManager boardManager, SoloPlayer player) {

        /* ask the PlayerTurn attribute that contains all the ActionToken and change the sequence */
        player.getGameSpace().increaseLorenzoIlMagnifico();
        player.getGameSpace().shuffleActionToken();
    }

    @Override
    public String toString() {
        return "PlusOne";
    }
}
