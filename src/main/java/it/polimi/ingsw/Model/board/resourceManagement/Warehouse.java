package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.*;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * It contains the Depots of the Warehouse.
 */

public abstract class Warehouse{
    protected ArrayList<Depot> depots;

    /**
     * Constructor
     * Creates three depots with increasing floor and size (ex. 1st floor, one size)
     */
    public Warehouse(){
        depots = new ArrayList<>();
        for(int i=0;i<3;i++){
            depots.add(new RealDepot(i+1,i+1));
        }
    }

    /**
     * Adds a resource in the depot chosen, after:
     * - Checking if the depot chosen is correct or exists;
     * - Checking if the resource you want to put in is not null.
     * @param resource -> The resource you want to put in
     * @param depot -> Which depot (the floor)
     * @throws InvalidActionException -> The thrown exception if it doesn't respect the checks.
     */
    public void addResource(Resource resource, int depot) throws InvalidActionException {
        if(depot<=0 || depot>depots.size()) throw new InvalidActionException("Choose a depot!");
        if(resource==null)throw new InvalidActionException("Choose a resource");
        if(!depots.contains(depots.get(depot-1)))throw new InvalidActionException("The depot doesn't exist!");
        depots.get(depot-1).addResource(resource);
    }

    /**
     * Removes a resource from the depot chosen, after:
     * - Checking if the depot chosen is correct or exists;
     * - Checking if the resource you want to remove is not null and exists
     * @param resource
     * @param depot
     * @throws InvalidActionException -> The thrown exception if it doesn't respect the checks
     */
    public void removeResource(Resource resource, int depot) throws InvalidActionException {
        if(depot<=0 || depot>depots.size()) throw new InvalidActionException("Choose a depot!");
        if(resource==null) throw new InvalidActionException("Choose a resource!");
        if(!depots.contains(depots.get(depot-1))) throw new InvalidActionException("The depot doesn't exist!");
        if(!depots.get(depot-1).getResources().contains(resource)) throw new InvalidActionException("The resource you want to remove doesn't exist!");
        depots.get(depot).removeResource();
    }

    /**
     * Moves a resource from depot1 to depot2, after:
     * - Checking if the resource is not null and exists in the depot1;
     * - The depots are correct and exist;
     * @param resource
     * @param depot1
     * @param depot2
     * @throws InvalidActionException
     */
    public void moveResource(Resource resource, int depot1, int depot2) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Choose a resource!");
        if(depot1<=0 || depot1>depots.size()) throw new InvalidActionException("Choose the first depot!");
        if(depot2<=0 || depot2>depots.size()) throw new InvalidActionException("Choose the second depot!");
        if(!depots.get(depot1-1).getResources().contains(resource))throw new InvalidActionException("The resource you want to move doesn't exist!");
        if(!depots.contains(depots.get(depot1-1)) || !depots.contains(depots.get(depot2-1))) throw new InvalidActionException("The depots or one of them don't exist!");
        depots.get(depot1-1).removeResource();
        depots.get(depot2-1).addResource(resource);
    }

    /**
     * Getter Method
     * @return -> The list of Depots
     */
    public ArrayList<Depot> getDepots() {
        return depots;
    }
}