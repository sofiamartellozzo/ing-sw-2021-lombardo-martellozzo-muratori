package it.polimi.ingsw.Model.actionAbility;

/*
 * SOFI*/

import it.polimi.ingsw.Model.BoardManager;
import it.polimi.ingsw.Model.SoloPlayer;

public class PlusUneAndShuffleActionAbility implements ActionAbility {
    @Override
    public void activeAbility(BoardManager boardManager, SoloPlayer player) {
        //move the black cross of plus one and shuffle all the cards
        //ask the PlayerTurn attribute that contains all the ActionTocken and change the sequence
        player.getGameSpace().increaseLorenzoIlMagnifico();
        player.getGameSpace().shuffleActionToken();
    }
}
