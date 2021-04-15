package it.polimi.ingsw.model.actionAbility;

import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.SoloPlayer;

/*
SOFI
 */

/**
 * One type of the ability that increase the faith traker of +2
 */

public class PlusTwoBlackCrossActionAbility implements ActionAbility{
    @Override
    public void activeAbility(BoardManager boardManager, SoloPlayer player) {
        /* increment position of the faith traker black of two, so two times */
        player.getGameSpace().increaseLorenzoIlMagnifico();
        player.getGameSpace().increaseLorenzoIlMagnifico();
    }
}
