package it.polimi.ingsw.model.actionAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.SoloPlayer;


/*
 * SOFI*/

/**
 * The ability that have an Action Token
 * (Only Solo Mode)
 */

public interface ActionAbility {
    public void activeAbility(BoardManager boardManager, SoloPlayer player) throws InvalidActionException;


}