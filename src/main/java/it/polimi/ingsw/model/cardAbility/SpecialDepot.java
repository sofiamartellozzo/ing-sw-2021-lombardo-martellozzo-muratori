package it.polimi.ingsw.model.cardAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.resourceManagement.SpecialWarehouse;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;


/**
 * GIANLUCA
 * TEST PASSED
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
        super(resource, TypeAbility.SPECIAL_DEPOT);
    }

    /**
     * It sets the active attribute to "true" (super.activeAbility(player)) and creates a Decorator class
     * to call the createDepot method with the resource supplied by the SpecialDepot Ability.
     * @param player
     */
    @Override
    public void activeAbility(Player player) throws InvalidActionException {
        super.activeAbility(player);
        Warehouse warehouse = player.getGameSpace().getResourceManager().getWarehouse();
        SpecialWarehouse specialWarehouse=new SpecialWarehouse(warehouse,this.getResource());
    }

    @Override
    public String toString() {
        return "SpecialDepot";
    }
}
