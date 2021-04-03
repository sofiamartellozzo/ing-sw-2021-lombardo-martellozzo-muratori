package it.polimi.ingsw.Model.board.resourceManagement;

/*
 * GIANLUCA
 * */

import com.sun.jdi.InvalidLineNumberException;
import it.polimi.ingsw.Exception.*;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * It extends warehouse at the begin of the game.
 */
public class WarehouseStandard extends Warehouse{

    /**
     * Constructor
     */
    public WarehouseStandard() {
        super();
    }

    /**
     * Adds a resource in the Depot.
     * @param resource -> The resource you want to put in
     * @param depot -> Which depot (the floor)
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Resource resource, int depot) throws InvalidActionException {
        super.addResource(resource,depot);
    }

    /**
     * Moves a resource from depot1 to depot2.
     * @param resource
     * @param depot1
     * @param depot2
     * @throws InvalidActionException
     */
    @Override
    public void moveResource(Resource resource, int depot1, int depot2) throws InvalidActionException {
        super.moveResource(resource, depot1, depot2);
    }

    /**
     * Removes a resource from the Depot.
     * @param resource
     * @param depot
     * @throws InvalidActionException
     */
    @Override
    public void removeResource(Resource resource, int depot) throws InvalidActionException {
        super.removeResource(resource, depot);
    }

    /**
     * Getter Method
     * @return -> The list of Depots.
     */
    @Override
    public ArrayList<Depot> getDepots() {
        return super.getDepots();
    }
}
