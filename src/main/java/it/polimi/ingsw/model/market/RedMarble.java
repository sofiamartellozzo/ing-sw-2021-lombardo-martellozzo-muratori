package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TypeResource;

import java.io.Serializable;

/*
 * SOFIA
 * */

/**
 * this class rapresent the specific Marble present in the Market
 * whith the color Red, so it has a different response when choose in the market
 * it doen't return a Resource but increment the position of the faith market of one
 */
public class RedMarble extends Marble implements Serializable {

    /* constructor of the class */
    public RedMarble() {
        this.color = Color.RED;
    }

    @Override
    public TypeResource choose(Player player) throws InvalidActionException {
        player.getGameSpace().getFaithTrack().increasePosition();
        return TypeResource.FAITHMARKER;
    }
}
