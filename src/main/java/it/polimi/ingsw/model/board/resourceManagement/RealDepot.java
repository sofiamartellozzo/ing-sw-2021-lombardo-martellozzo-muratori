package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * TEST PASSED
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
    @Override
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
        if(resource==null) throw new InvalidActionException("Uncorrect reosurce");
        if(isFull()) throw new InvalidActionException("The depot is full!");
        if(!resources.isEmpty() && !getType().equals(resource.getType())) throw new InvalidActionException("The resource you want to put in isn't the same type of the content!");
        resources.add(resource);
    }

    /**
     * Removes one resource at the end in the ArrayList, so from the depot, after checking if the depot isn't empty, else throws Exception.
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
        if(resources.size()==size){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Get the type of the content of the depot, if the depot is empty return null.
     * @return
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
     * After checking if:
     * - The input is correct, all resources you want to put in must be of the same type;
     * - The depot is not full;
     * - The type of resources in input is the same of the content of the depot, in case the depot is not empty;
     * - There's enough space.
     * Adds all resources in the depot.
     * @param num
     * @param resource
     * @throws InvalidActionException
     */
    @Override
    public void addResources(int num,Resource resource) throws InvalidActionException {
        if (resource==null) throw new InvalidActionException("Uncorrect resources!");
        if(!resources.isEmpty() && !getType().equals(resource.getType())) throw new InvalidActionException("The resources you want to put in isn't the same type of the content!");
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