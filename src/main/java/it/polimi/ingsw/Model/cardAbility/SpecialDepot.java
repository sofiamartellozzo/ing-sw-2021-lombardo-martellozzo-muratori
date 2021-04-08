package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.board.resourceManagement.Decorator;
import it.polimi.ingsw.Model.board.resourceManagement.SpecialWarehouse;
import it.polimi.ingsw.Model.board.resourceManagement.Warehouse;


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
        Warehouse warehouse = player.getGameSpace().getResourceManager().getWarehouse();
        SpecialWarehouse specialWarehouse=null;
        if(warehouse.toString().equals("WarehouseStandard")) {
            specialWarehouse = new SpecialWarehouse(warehouse);
        }else if(warehouse.toString().equals("SpecialWarehouse")){
            specialWarehouse = (SpecialWarehouse) warehouse;
        }
        specialWarehouse.createDepot(this.getResource());
        player.getGameSpace().getResourceManager().setWarehouseStandard(specialWarehouse);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
