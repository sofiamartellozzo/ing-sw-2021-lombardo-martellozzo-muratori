package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Implements Depot interface.
 * It represents the depots made by the special ability "SpecialDepot",
 * The "resources" attribute represents the content of the depot.
 * The "type" attribute indicates what resource type the depot can contain.
 * The "floor" indicates if it is the 1st, 2nd, 3rd.
 * */
public class RealDepot implements Depot, Serializable {
    private final ArrayList<Resource> resources;
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
     * @return -> The number of the "floor"/"depot"
     */
    @Override
    public int getFloor() {
        return floor;
    }

    @Override
    public int getNumberResources() {
        return resources.size();
    }

    /**
     * @return -> How many resource the depot can contain.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Adds the "resource" in the depot.
     * After checking:
     * - The resource is not null;
     * - The depot is not full;
     * - If the depot is not empty, the resource must be the same type of depot
     * @param resource -> The resource you want to put in (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    @Override
    public void addResource(Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Incorrect resource");
        if(isFull()) throw new InvalidActionException("The depot is full!");
        if(!resources.isEmpty() && !getType().equals(resource.getType())) throw new InvalidActionException("The resource you want to put in isn't the same type of the content!");
        resources.add(resource);
    }

    /**
     * Removes a resource from the depot.
     * After checking:
     * - The depot is not empty.
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    @Override
    public void removeResource() throws InvalidActionException {
        if(resources.isEmpty()) throw new InvalidActionException("The depot is empty!");
        resources.remove(resources.size()-1);
    }

    /**
     * @return -> True if the depot is full, else false.
     */
    @Override
    public boolean isFull() {
        return resources.size() == size;
    }

    @Override
    public boolean isEmpty() {
        return resources.isEmpty();
    }

    /**
     * Getter Method
     * @return -> The content type of the depot, if the depot is empty return null.
     */
    @Override
    public TypeResource getType() {
        if(!resources.isEmpty()){
            return resources.get(0).getType();
        }else{
            return null;
        }
    }

    /**
     * Adds "n" resources of "resource".
     * After checking:
     * - The resource is not null;
     * - If the depot is not empty, the resource must be of the same type of the depot content.
     * - There's enough space or is not full.
     * @param n -> The number of resource you want to put in
     * @param resource -> The resource you want to put in
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    @Override
    public void addResources(int n,Resource resource) throws InvalidActionException {
        if (resource==null) throw new InvalidActionException("Incorrect resources!");
        if(!resources.isEmpty() && !getType().equals(resource.getType())) throw new InvalidActionException("The resources you want to put in isn't the same type of the content!");
        if(n>getSize()-this.resources.size()) throw new InvalidActionException("There's no space!");
        for(int i=0;i<n;i++){
            resources.add(resource);
        }
    }

    /**
     * Removes "n" resources from the depot.
     * After checking:
     * - The depot is not empty;
     * - The depot contains enough resources to remove.
     * @param n -> The number of resources you want to remove
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    @Override
    public void removeResources(int n) throws InvalidActionException {
        if(this.resources.isEmpty()) throw new InvalidActionException("The depot is empty!");
        if(resources.size()<n) throw new InvalidActionException("You're trying to remove more than how many it contains!");
        for(int i=0;i<n;i++){
            resources.remove(resources.size()-1);
        }
    }
}