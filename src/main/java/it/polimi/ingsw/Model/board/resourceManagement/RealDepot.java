package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * Implements Depot interface.
 * It represents one of the three depots from the warehouse.
 * resources -> Indicates what and how many resources are in the depot
 * */

public class RealDepot implements Depot{
    private ArrayList<Resource> resources;
    private final int floor;
    private final int size;

    /**
     * Constructor
     * Initializing ArrayList<Resource> resource
     * @param floor -> Indicates which of the three floors/depots
     * @param size -> Indicates how many resources the depot can contain
     */
    public RealDepot(int floor, int size) {
        this.resources= new ArrayList<>();
        this.floor = floor;
        this.size=size;
    }

    /**
     * Getter Method
     * @return -> The resources contained by the Depot
     */
    public ArrayList<Resource> getResources() {
        return resources;
    }

    /**
     * Getter Method
     * @return -> The number of the floor/depot, for istance 1 indicates first floor/depot
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Getter Method
     * @return -> The size of the depot, how many resources it can contained
     */
    public int getSize() {
        return size;
    }

    /**
     * Adds one resource at the end of the ArrayList, so in the depot, after:
     * - Checking if the depot isn't full
     * - Checking if the resource we want to put into is the same type of the resources already contained in the ArrayList, if there are.
     * If one of this condition isn't satisfied, the method throws the relative Exception.
     * @param resource -> The resource you want to put in the depot (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Resource resource) throws InvalidActionException {
        if(resources.size()>=getSize()) throw new InvalidActionException("The depot is full!");
        if(!resources.isEmpty() && !resources.contains(resource)) throw new InvalidActionException("The resource you want to put in isn't the same type of the content!");
        resources.add(resource);
    }

    /**
     * Removes one resource at the end in the ArrayList, so from the depot, after checking if the depot isn't empty, else throws Exception.
     * @throws InvalidActionException
     */
    @Override
    public void removeResource() throws InvalidActionException {
        if(resources.isEmpty()) throw new InvalidActionException("The depot is empty!");
        resources.remove(resources.size());
    }
}