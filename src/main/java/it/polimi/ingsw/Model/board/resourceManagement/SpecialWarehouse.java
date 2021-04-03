package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * Decorator Pattern
 * When the "SpecialDepot" ability activates, it creates a new Special Warehouse with the existing old depots of the WarehouseStandard,
 * decorated by the new created Ability Depot.
 */
public class SpecialWarehouse extends Decorator{

    /**
     * Constructor
     * @param warehouse
     */
    public SpecialWarehouse(Warehouse warehouse) {
        super(warehouse);
    }

    /**
     * Getter Method
     * @return
     */
    @Override
    public ArrayList<Depot> getDepots() {
        return super.getDepots();
    }

    /**
     * Creates a new Ability Depot
     * @param resource -> The resource you can put in the new created Depot (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException
     */
    @Override
    public void createDepot(Resource resource) throws InvalidActionException {
        super.createDepot(resource);
    }

    /**
     * Adds the specified resource in the chosen depot
     * @param resource
     * @param depot
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Resource resource, int depot) throws InvalidActionException {
        super.addResource(resource, depot);
    }

    /**
     * Removes a resource from the chosen depot
     * @param resource
     * @param depot
     * @throws InvalidActionException
     */
    @Override
    public void removeResource(Resource resource, int depot) throws InvalidActionException {
        super.removeResource(resource, depot);
    }

    /**
     * Moves a chosen resource from the depot1 to the depot2.
     * @param resource
     * @param depot1
     * @param depot2
     * @throws InvalidActionException
     */
    @Override
    public void moveResource(Resource resource, int depot1, int depot2) throws InvalidActionException {
        super.moveResource(resource, depot1, depot2);
    }
}
