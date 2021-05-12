package it.polimi.ingsw.model.actionAbility;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.SoloPlayer;

import java.io.Serializable;

/*
 * SOFI*/

/**
 * One type of the ability that increase the faith traker of +1 and shuffle the action token deck
 */

public class PlusUneAndShuffleActionAbility implements ActionAbility, Serializable {
    @Override
    public void activeAbility(BoardManager boardManager, SoloPlayer player) {
        /* move the black cross of plus one and shuffle all the cards
        ** ask the PlayerTurn attribute that contains all the ActionTocken and change the sequence */
        player.getGameSpace().increaseLorenzoIlMagnifico();
        player.getGameSpace().shuffleActionToken();
    }
}
