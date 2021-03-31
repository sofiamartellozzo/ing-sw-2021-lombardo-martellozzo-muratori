package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

/**
 * GIANLUCA
 * It refers to the ability of the Discount when the relative Player
 * buy a Development Card
 */

public class Discount extends SpecialAbility{
    /**
     * Constructor
     * @param resource
     */
    public Discount(Resource resource) {
        super(resource);
    }

    /**
     *
     */
    @Override
    public void activeAbility(Player player) {
        super.activeAbility(player);
    }
}
