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
     * @param depot1
     * @param depot2
     * @throws InvalidActionException
     */
    @Override
    public void moveResource(int depot1, int depot2) throws InvalidActionException {
        super.moveResource(depot1, depot2);
    }

    /**
     * Checks if there's some depot where the player can put the resource in input.
     * First, checks in the normal depot of the warehouse,
     * then, if there are, checks in the special depots created by the Special Depot ability.
     * @param resource
     * @return
     */
    @Override
    public boolean checkAvailableDepot(Resource resource) {
        return super.checkAvailableDepot(resource);
    }

    /**
     * Checks if the resource is contained by other NORMAL depots different from the chosen depot.
     * @param resource
     * @param depot
     * @return
     */
    @Override
    public boolean checkResourceInSomeDepot(Resource resource, int depot) {
        return super.checkResourceInSomeDepot(resource, depot);
    }


    /**
     * Gets the content of ALL depots (normal and special) in an ArrayList of Resources.
     * @return
     */
    @Override
    public ArrayList<Resource> getContent() {
        return super.getContent();
    }

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "SpecialWarehouse";
    }
}
