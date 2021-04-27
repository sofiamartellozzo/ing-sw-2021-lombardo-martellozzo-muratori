package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.exception.*;

import java.util.ArrayList;
/**
 * Implements Depot interface.
 * It represents the depots made by the special ability "SpecialDepot",
 * The "resources" attribute represents the content of the depot.
 * The "type" attribute indicates what resource type the depot can contain.
 * The "depot" is the same of the "floor" of the RealDepot object (in this case can assume 4 or 5)
 * */
public class AbilityDepot implements Depot{
    private final ArrayList<Resource> resources;
    private final TypeResource type;
    private final int depot;

    /**
     * Constructor
     * Initializing ArrayList<Resource> resource
     * @param type -> Indicates what type of resource can be add in the depot, it is specified by the special ability
     * @param depot -> Indicates which depot (in case of two ability, they can be two), it corresponds to the floor of the depot
     */
    public AbilityDepot(TypeResource type, int depot) {
        this.resources = new ArrayList<>();
        this.type = type;
        this.depot = depot;
    }

    /**
     * Getter Method
     * @return -> The resources contained by the Depot
     */
    @Override
    public ArrayList<Resource> getResources() {
        return resources;
    }

    /**
     * Adds the "resource" in the depot.
     * After checking:
     * - The resource is not null;
     * - The depot is not fulls;
     * - The resource is the same type of depot
     * @param resource -> The resource you want to put in (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    @Override
    public void addResource(Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Incorrect resource!");
        if(isFull()) throw new InvalidActionException("The depot is full!");
        if(!resource.getType().equals(type)) throw new InvalidActionException("The resource you want to put in isn't the same type of the content!");
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
        return resources.size() == 2;
    }

    /**
     * Getter Method
     * @return -> The content type of the depot.
     */
    @Override
    public TypeResource getType() {
        return type;
    }

    /**
     * @return -> How many resource the depot can contain.
     */
    @Override
    public int getSize(){
        return 2;
    }

    /**
     * Adds "n" resources of "resource".
     * After checking:
     * - The resource is not null;
     * - The resource is of the same type of the depot content.
     * - There's enough space or is not full.
     * @param n -> The number of resource you want to put in
     * @param resource -> The resource you want to put in
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    @Override
    public void addResources(int n, Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Incorrect resources!");
        if(!getType().equals(resource.getType())) throw new InvalidActionException("The resources you want to put in isn't the same type of the content!");
        if(isFull() || n>getSize()-this.resources.size()) throw new InvalidActionException("There's no space!");
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

    /**
     * Getter Method
     * @return -> The number of the "floor"/"depot"
     */
    @Override
    public int getFloor() {
        return depot;
    }
}
