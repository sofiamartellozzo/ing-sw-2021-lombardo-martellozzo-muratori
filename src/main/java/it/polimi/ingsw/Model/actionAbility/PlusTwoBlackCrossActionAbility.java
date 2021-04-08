package it.polimi.ingsw.Model.actionAbility;

/*
 * SOFI*/

import it.polimi.ingsw.Model.BoardManager;
import it.polimi.ingsw.Model.SoloPlayer;

public class PlusTwoBlackCrossActionAbility implements ActionAbility{
    @Override
    public void activeAbility(BoardManager boardManager, SoloPlayer player) {
        //increment position() of the faith traker black of two, so two times
        player.getGameSpace().increaseLorenzoIlMagnifico();
        player.getGameSpace().increaseLorenzoIlMagnifico();
    }
}
