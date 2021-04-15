package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.exception.*;

import java.util.ArrayList;
/**
 * GIANLUCA
 * TEST PASSED
 * Implements Depot interface.
 * It represents the depots made by the special ability "SpecialDepot",
 * the size is always 2, so it doesn't need a "size" attribute as the RealDepot class.
 * resources -> indicates what and how many resources are in the depot;
 * */
public class AbilityDepot implements Depot{
    private ArrayList<Resource> resources;
    private final TypeResource type;
    private final int depot;

    /**
     * Constructor
     * Initializing ArrayList<Resource> resource
     * @param type -> Indicates what type of resource can be add in the depot, it is specified by the special ability
     * @param depot -> Indicates which depot (in case of two ability, they can be two)
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
     * It adds at the end in the ArrayList one resource,
     * after checking if the depot isn't full
     * and checking if the resource we want to put
     * into respects the TypeResource content attribute.
     * If one of this condition isn't satisfied
     * the method throws the relative Exception.
     * @param resource -> The resource you want to put in (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Uncorrect resource!");
        if(isFull()) throw new InvalidActionException("The depot is full!");
        if(!resource.getType().equals(type)) throw new InvalidActionException("The resource you want to put in isn't the same type of the content!");
        resources.add(resource);
    }

    /**
     * It removes at the end in the ArrayList one resource,
     * after checking if the depot isn't empty, else throws Exception.
     * @throws InvalidActionException
     */
    @Override
    public void removeResource() throws InvalidActionException {
        if(resources.isEmpty()) throw new InvalidActionException("The depot is empty!");
        resources.remove(resources.size()-1);
    }

    /**
     * Checks if the depot is full.
     * @return
     */
    @Override
    public boolean isFull() {
        return resources.size() == 2;
    }

    /**
     * Getter Method
     * @return
     */
    @Override
    public TypeResource getType() {
        return type;
    }

    /**
     * Return the size (2 resources) of the depot.
     * @return
     */
    @Override
    public int getSize(){
        return 2;
    }

    /**
     * After checking if:
     * - The input is correct, all resources you want to put in must be of the same type;
     * - The depot is not full;
     * - The type of resources in input is the same of the content of the depot, in case the depot is not empty;
     * - There's enough space.
     * Adds all resources in the depot.
     * @param resource
     * @throws InvalidActionException
     */
    @Override
    public void addResources(int num, Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Uncorrect resources!");
        if(!getType().equals(resource.getType())) throw new InvalidActionException("The resources you want to put in isn't the same type of the content!");
        if(num>getSize()-this.resources.size()) throw new InvalidActionException("There's no space!");
        for(int i=0;i<num;i++){
            resources.add(resource);
        }

    }

    /**
     * After checking if:
     * - The depot is not empty;
     * - The number of resources you want to remove is less than or equals to those that the depot contains.
     * Removes the number of resources indicated.
     * @param num
     * @throws InvalidActionException
     */
    @Override
    public void removeResources(int num) throws InvalidActionException {
        if(this.resources.isEmpty()) throw new InvalidActionException("The depot is empty!");
        if(resources.size()<num) throw new InvalidActionException("You're trying to remove more than how many it contains!");
        for(int i=0;i<num;i++){
            resources.remove(resources.size()-1);
        }
    }
}
