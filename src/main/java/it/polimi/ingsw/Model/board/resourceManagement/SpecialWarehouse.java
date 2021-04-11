package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * TEST PASSED
 * Decorator Pattern
 * When the "SpecialDepot" ability activates, it creates a new Special Warehouse with the existing old depots of the WarehouseStandard,
 * decorated by the new created Ability Depot.
 */
public class SpecialWarehouse extends Decorator{

    /**
     * Constructor
     * @param warehouse
     */
    public SpecialWarehouse(Warehouse warehouse,Resource resource) throws InvalidActionException {
        this.warehouse=warehouse;
        createDepot(resource);
    }


    /**
     * Create a new AbilityDepot object with the resource parameter, which
     * obliged the player to put just that type of resource in,
     * and it's numerated according to the size of the depots (ArrayList).
     * For instance, if there aren't any AbilityDepot, size is 3, creating a new AbilityDepot
     * will be 4. The same reasoning for the 5th Depot.
     * @param resource -> The resource you can put in the new created Depot (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException
     */
    private void createDepot(Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Insert a correct resource!");
        AbilityDepot abilityDepot = new AbilityDepot(resource.getType(),depots.size()+1);
        warehouse.getDepots().add(abilityDepot);
    }
}
