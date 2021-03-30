package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;


/**
 * GIANLUCA
 * It creates a Special Depot in which the Player can insert
 * maximum two Resources (indicated by the "type" attribute
 * of the SpecialAbility abstract class
 */

public class SpecialDepot extends SpecialAbility{
    /**
     * Constructor
     * @param resource
     */
    public SpecialDepot(Resource resource) {
        super(resource);
    }

    /**
     *
     */
    @Override
    public void activeAbility(Player player) {
        super.activeAbility(player);
        //New BuyDiscount
    }
}
