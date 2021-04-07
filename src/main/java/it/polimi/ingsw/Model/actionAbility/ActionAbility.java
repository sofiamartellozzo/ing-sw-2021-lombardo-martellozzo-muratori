package it.polimi.ingsw.Model.actionAbility;

import it.polimi.ingsw.Model.BoardManager;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.SoloPlayer;

/*
 * SOFI*/

/**
 * The ability that have an Action Token
 * (Only Solo Mode)
 */

public interface ActionAbility {
    public void activeAbility(BoardManager boardManager, SoloPlayer player);
}
