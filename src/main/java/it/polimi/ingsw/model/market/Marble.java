package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TypeResource;

import java.io.Serializable;

/*
 * SOFIA
 * */
public abstract class Marble implements Serializable {
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
    public abstract TypeResource choose(Player player) throws InvalidActionException;
}
