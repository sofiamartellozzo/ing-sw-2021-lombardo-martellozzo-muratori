package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * It contains the Depots of the Warehouse.
 */

public abstract class Warehouse {
    protected ArrayList<Depot> depots;

    /**
     * Constructor
     * Creates three depots with increasing floor and size (ex. 1st floor, one size)
     */
    public Warehouse() {
        depots = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            depots.add(new RealDepot(i + 1, i + 1));
        }
    }

    /**
     * Adds a resource in the depot chosen, after:
     * - Checking if the depot chosen is correct or exists;
     * - Checking if the resource you want to put in is not null.
     *
     * @param resource -> The resource you want to put in
     * @param depot    -> Which depot (the floor)
     * @throws InvalidActionException -> The thrown exception if it doesn't respect the checks.
     */
    public void addResource(Resource resource, int depot) throws InvalidActionException {
        if ((depot < 1) || (depot > depots.size())) throw new InvalidActionException("Choose a depot!");
        Depot depot1=depots.get(depot-1);
        if (!checkAvailableDepot(resource)) throw new InvalidActionException("The resource can't be add in any depot, move some resources or discard it");
        depot1.addResource(resource);
    }

    /**
     * Removes a resource from the depot chosen, after:
     * - Checking if the depot chosen is correct or exists;
     * - Checking if the resource you want to remove is not null and exists
     *
     * @param resource
     * @param depot
     * @throws InvalidActionException -> The thrown exception if it doesn't respect the checks
     */
    public void removeResource(int depot) throws InvalidActionException {
        if (depot <= 0 || depot > depots.size()) throw new InvalidActionException("Choose a depot!");
        depots.get(depot-1).removeResource();
    }

    /**
     * CHANGE NAME -> moveResourceToAbilityDepot
     * Moves 1 resource to another depot (made only for moving a resource in an AbilityDepot)
     * @param fromDepot
     * @param toDepot
     */
    public void moveResource(int fromDepot,int toDepot) throws InvalidActionException {
        if(fromDepot==toDepot) throw new InvalidActionException("You're moving in the same depot!");
        if (depots.size()==3) throw new InvalidActionException("There's no depot you can move it in!");
        if (toDepot!=4 && toDepot!=5) throw new InvalidActionException("Choose a second ability depot!");
        Resource resourceToMove = depots.get(fromDepot-1).getResources().get(0);
        addResource(resourceToMove,toDepot);
        removeResource(fromDepot);
    }

    /**
     * Moves all resources from depot1 to depot2, after:
     * - Checking if the resource is not null and exists in the depot1;
     * - The depots are correct and exist;
     * -
     * @param depot1
     * @param depot2
     * @throws InvalidActionException
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
     *
     * @return -> The list of Depots
     */
    public ArrayList<Depot> getDepots() {
        return depots;
    }

    /**
     * Checks if there's some depot where the player can put the resource in input.
     * First, checks in the normal depot of the warehouse,
     * then, if there are, checks in the special depots created by the Special Depot ability.
     * @param resource
     * @return
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
     * Checks if the resource is contained by other NORMAL depots different from the chosen depot.
     * @param resource
     * @param depot
     * @return
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
     * Gets the content of ALL depots (normal and special) in an ArrayList of Resources.
     * @return
     */
    public ArrayList<Resource> getContent(){
        ArrayList<Resource> content = new ArrayList<>();
        for(Depot depot:depots){
            content.addAll(depot.getResources());
        }
        return content;
    }

    public void removeResources(ArrayList<Resource> resources) throws InvalidActionException {
        if(!resources.isEmpty()){
            if(!checkEnoughResources(resources)) throw new InvalidActionException("The Warehouse doesn't contain some resources you want to remove!");
            for(Resource resource:resources){
                depots.get(searchResource(resource)-1).removeResource();
            }
        }
    }

    private int countResource(ArrayList<Resource> resources, Resource resource){
        int count = (int) resources.stream().filter(r -> r.getType().equals(resource.getType())).count();
        return count;
    }

    private boolean checkEnoughResources(ArrayList<Resource> resources){
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

    public int searchResource(Resource resource){
        for(int i=0; i< depots.size(); i++){
            if(depots.get(i).getType()!=null && depots.get(i).getType().equals(resource.getType())){
                return i+1;
            }
        }
        return -1;
    }
}