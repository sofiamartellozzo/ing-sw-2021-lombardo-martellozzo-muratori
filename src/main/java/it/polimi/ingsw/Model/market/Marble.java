package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

/*
 * SOFIA
 * */
public abstract class Marble {
    protected Color color;

    public Color getColor() {
        return color;
    }

    /**
     * method called when the player select that marble from the market
     * based on wich marble this will return the right Resource or move
     * the Faith Marker
     *
     * @param player
     * @return
     */
    public abstract void choose(Player player) throws InvalidActionException;
}
