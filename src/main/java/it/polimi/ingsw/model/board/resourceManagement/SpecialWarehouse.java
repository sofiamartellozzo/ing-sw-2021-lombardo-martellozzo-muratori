package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Resource;

/**
 * Decorator Pattern
 * When the "SpecialDepot" ability activates,
 * adds a new AbilityDepot in the warehouse
 * decorated by the new created Ability Depot.
 */
public class SpecialWarehouse extends Decorator{

    /**
     * Constructor
     * @param warehouse -> The reference to the warehouse
     * @param resource -> The type of the new depot
     * @throws InvalidActionException -> If one of the condition is not respected
     */
    public SpecialWarehouse(Warehouse warehouse,Resource resource) throws InvalidActionException {
        this.warehouse=warehouse;
        createDepot(resource);
    }


    /**
     * PRIVATE METHOD
     * Create a new AbilityDepot object of type "resource" and add it to the warehouse.
     * After checking:
     * - The "resource" is not null.
     * @param resource -> The type of content of the new depot.
     * @throws InvalidActionException -> If one of the condition is not respected
     * NOTE: supports the constructor
     */
    private void createDepot(Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Insert a correct resource!");
        AbilityDepot abilityDepot = new AbilityDepot(resource.getType(),depots.size()+1);
        warehouse.getDepots().add(abilityDepot);
    }
}
