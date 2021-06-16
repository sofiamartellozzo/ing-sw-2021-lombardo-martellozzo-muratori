package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TypeResource;

import java.io.Serializable;


/**
 * this class represents a specific Marble that is present in the Market (the RED marble)
 * it doesn't return a Resource but increments the position of the faith marker of one
 */
public class RedMarble extends Marble implements Serializable {

    /**
     * constructor of the class
     */
    public RedMarble() {
        this.color = Color.RED;
    }

    @Override
    public TypeResource choose(Player player) throws InvalidActionException {
        return TypeResource.FAITHMARKER;
    }
}
