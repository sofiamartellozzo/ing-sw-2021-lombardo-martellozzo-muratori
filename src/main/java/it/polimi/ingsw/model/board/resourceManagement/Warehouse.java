package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the Warehouse.
 * The "depots" attribute contains the depots which compose the warehouse.
 */

public abstract class Warehouse {
    protected ArrayList<Depot> depots;

    /**
     * Constructor
     * Initialize "depots", adding three RealDepot of increasing size and floor
     */
    public Warehouse() {
        depots = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            depots.add(new RealDepot(i + 1, i + 1));
        }
    }

    /**
     * Add "resource" in "depot".
     * After checking:
     * - "depot" must be one of those contained by the warehouse
     * - exists a depot where the resource can be put
     * @param resource -> The resource you want to add
     * @param depot -> The depot where to add the resource
     * @throws InvalidActionException -> If one of the conditions is not respected.
     */
    public void addResource(Resource resource, int depot) throws InvalidActionException {
        if ((depot < 1) || (depot > depots.size())) throw new InvalidActionException("Choose a depot!");
        Depot depot1=depots.get(depot-1);
        if (!checkAvailableDepot(resource)) throw new InvalidActionException("The resource can't be add in any depot, move some resources or discard it");
        depot1.addResource(resource);
    }

    /**
     * Remove a resource from "depot".
     * After checking:
     * - "depot" must be one of those contained by the warehouse.
     * @param depot -> The depot where to remove the resource
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResource(int depot) throws InvalidActionException {
        if (depot <= 0 || depot > depots.size()) throw new InvalidActionException("Choose a depot!");
        depots.get(depot-1).removeResource();
    }

    /**
     * Usable just in case exists at least an AbilityDepot object in the warehouse.
     * Move one resource from "fromDepot" to "toDepot".
     * After checking:
     * - "fromDepot" and "toDepot" must be different.
     * - If there are more than 3 depots in the warehouse, this means exists at least an AbilityDepot.
     * - The "fromDepot" and/or "toDepot" parameters must refer to an AbilityDepot.
     * @param fromDepot -> The depot where move the resource from
     * @param toDepot -> The depot where move the resource to
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void moveResourceToAbilityDepot(int fromDepot, int toDepot) throws InvalidActionException {
        if(fromDepot==toDepot) throw new InvalidActionException("You're moving in the same depot!");
        if (depots.size()==3) throw new InvalidActionException("There's no depot you can move it in!");
        if (fromDepot <= 0 || fromDepot > depots.size()) throw new InvalidActionException("Choose the first depot!");
        if (toDepot <= 0 || toDepot > depots.size()) throw new InvalidActionException("Choose the second depot!");
        if ((fromDepot!=4 && fromDepot!=5)&&(toDepot!=4 && toDepot!=5)) throw new InvalidActionException("Choose an ability depot!");
        Resource resourceToMove = depots.get(fromDepot-1).getResources().get(0);
        addResource(resourceToMove,toDepot);
        removeResource(fromDepot);
    }

    /**
     * Moves all resources from "fromDepot" to "toDepot".
     * After checking:
     * - "fromDepot" and "toDepot" must be different.
     * - "fromDepot" and "toDepot" must refers to one of the depot of the warehouse.
     * @param fromDepot -> The depot where move the resources from
     * @param toDepot -> The depot where move the resources to
     * @throws InvalidActionException -> If one of the condition is not respected
     */
    public void moveResources(int fromDepot, int toDepot) throws InvalidActionException {
        if (fromDepot==toDepot) throw new InvalidActionException("You're moving in the same depot!");
        if (fromDepot <= 0 || fromDepot > depots.size()) throw new InvalidActionException("Choose the first depot!");
        if (toDepot <= 0 || toDepot > depots.size()) throw new InvalidActionException("Choose the second depot!");
        Depot depot1 = depots.get(fromDepot-1);
        Depot depot2 = depots.get(toDepot-1);
        Resource movingResource = depot1.getResources().get(0);
        int howManyResource = depot1.getResources().size();
        depot1.removeResources(howManyResource);
        depot2.addResources(howManyResource,movingResource);
    }

    /**
     * Getter Method
     * @return -> The "depots" attribute as an ArrayList<Depot>
     */
    public ArrayList<Depot> getDepots() {
        return depots;
    }

    /**
     * Check if a resource can be put in at least one depot.
     * @param resource -> The resource you would put.
     * @return -> True if the resource can be put, else false.
     */
    public boolean checkAvailableDepot(Resource resource) {
       for(int i=0;i<3;i++){
           if((depots.get(i).getResources().isEmpty()) && (!checkResourceInSomeDepot(resource,i+1))){
               return true;
           }else {
               TypeResource type = depots.get(i).getType();
               if (!depots.get(i).isFull() && resource.getType().equals(type)) {
                   return true;
               }
           }
       }
       for(int i=3;i<depots.size();i++){
           if(!depots.get(i).isFull() && resource.getType().equals(depots.get(i).getType())){
               return true;
           }
       }
       return false;
    }

    /**
     * Checks if one resource can be put "depot".
     * @param resource -> The resource you want to check
     * @param depot -> The depot where you would put in the resource
     * @return -> True if the "resource" can be put in "depot", else false.
     */
    private boolean checkResourceInSomeDepot(Resource resource, int depot){
        for(int i=0;i<3;i++){
            if((i+1!=depot) && (!depots.get(i).getResources().isEmpty()) && (depots.get(i).getType().equals(resource.getType())) && (depots.get(i).getType()!=null)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return -> The content of the warehouse as an ArrayList<Resource>
     */
    public ArrayList<Resource> getContent(){
        ArrayList<Resource> content = new ArrayList<>();
        for(Depot depot:depots){
            content.addAll(depot.getResources());
        }
        return content;
    }

    /**
     * Remove all "resources" from the warehouse.
     * After checking:
     * - "resources" is not null and not empty
     * - All "resources" must be contained by the warehouse.
     * @param resources -> The resources you want to remove
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResources(ArrayList<Resource> resources) throws InvalidActionException {
        if(resources==null || resources.isEmpty()) throw new InvalidActionException("There's no resource to remove");
        if(!checkEnoughResources(resources)) throw new InvalidActionException("The Warehouse doesn't contain some resources you want to remove!");
        for(Resource resource:resources){
            depots.get(searchResource(resource)-1).removeResource();
        }
    }

    /**
     * Count how many resources of type "resource" there are in the "content".
     * @param content -> The content where to count
     * @param resource -> The resource to count
     * @return -> The number of resources
     */
    public int countResource(ArrayList<Resource> content, Resource resource){
        int count = (int) content.stream().filter(r -> r.getType().equals(resource.getType())).count();
        return count;
    }

    /**
     * Checks if all "resources" are contained in the warehouse
     * @param resources -> The resources to check
     * @return -> True if all "resources" are contained, else false.
     */
    public boolean checkEnoughResources(ArrayList<Resource> resources){
        ArrayList<Resource> typeResources = new ArrayList<>();
        typeResources.add(new Resource(Color.YELLOW));
        typeResources.add(new Resource(Color.BLUE));
        typeResources.add(new Resource(Color.PURPLE));
        typeResources.add(new Resource(Color.GREY));
        for(Resource resource: typeResources){
            if(countResource(getContent(),resource)<countResource(resources,resource)) return false;
        }
        return true;
    }

    /**
     * Search the "resource" in the warehouse.
     * @param resource -> The resource to search
     * @return -> The depot floor where the resource was found, else -1
     */
    public int searchResource(Resource resource){
        for(int i=0; i< depots.size(); i++){
            if(depots.get(i).getType()!=null && depots.get(i).getType().equals(resource.getType())){
                return i+1;
            }
        }
        return -1;
    }

    /**
     * @return -> A photographic situation of the warehouse
     */
    public HashMap<Integer,ArrayList<TypeResource>> getInstanceContent(){
        HashMap<Integer,ArrayList<TypeResource>> content = new HashMap<Integer,ArrayList<TypeResource>>();
        for(Depot depot:depots){
            TypeResource type = depot.getType();
            ArrayList<TypeResource> resources = new ArrayList<>();
            for (int i = 0; i < depot.getResources().size(); i++) {
                resources.add(type);
            }
            content.put(depot.getFloor(),resources);
        }
        return content;
    }

    /**
     * After checking:
     * - The "resource" is not null
     * @param resource -> The resource you want to put in the warehouse
     * @return -> An ArrayList<Integer> which contains which floor of the depot are available to put the "reosurce"
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public ArrayList<Integer> inWhichDepot(Resource resource) throws InvalidActionException {
        ArrayList<Integer> whichDepot = new ArrayList<>();
        if(resource==null) throw new InvalidActionException("Resource not valid");
        for(Depot depot:depots){
            if(depot.getResources().isEmpty() && depot instanceof RealDepot && searchResource(resource)==-1){
                whichDepot.add(depot.getFloor());
            }else if(!depot.isFull() && depot.getType().equals(resource.getType())){
                whichDepot.add(depot.getFloor());
            }
        }
        return whichDepot;
    }

}