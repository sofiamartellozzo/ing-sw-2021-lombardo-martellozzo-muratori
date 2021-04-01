package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.board.resourceManagement.Decorator;


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
     * It sets the active attribute to "true" (super.activeAbility(player)) and creates a Decorator class
     * to call the createDepot method with the resource supplied by the SpecialDepot Ability.
     * @param player
     */
    @Override
    public void activeAbility(Player player) throws InvalidActionException {
        super.activeAbility(player);
        Decorator decorator = new Decorator();
        decorator.createDepot(this.getResource());
    }
}
