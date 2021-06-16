package it.polimi.ingsw.model.actionAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.SoloPlayer;

/**
 * The ability that has an Action Token
 * (Only in Solo Mode)
 */

public interface ActionAbility {
    public void activeAbility(BoardManager boardManager, SoloPlayer player) throws InvalidActionException;


}