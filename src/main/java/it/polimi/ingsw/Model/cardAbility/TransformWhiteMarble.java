package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.market.Ability;
import it.polimi.ingsw.Model.market.MarbleSpecial;


/**
 * GIANLUCA
 * It allows to the Player to take one Resource (indicated by the Special Ability)
 * for each White Marble he draws
* */

public class TransformWhiteMarble extends SpecialAbility{
    /**
     * Constructor
     * @param resource
     */
    public TransformWhiteMarble(Resource resource) {
        super(resource);
    }

    /**
     *
     */
    @Override
    public void activeAbility(Player player) throws InvalidActionException {
        super.activeAbility(player);
        Ability ability = new Ability(getResource());
        player.setWhiteSpecialMarble(new MarbleSpecial());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
