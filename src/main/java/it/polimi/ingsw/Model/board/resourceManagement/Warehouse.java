package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.*;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;

import java.lang.reflect.Array;
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
        if (depot <= 0 || depot > depots.size()) throw new InvalidActionException("Choose a depot!");
        if (resource == null) throw new InvalidActionException("Resource not valid");
        if (!depots.contains(depots.get(depot - 1))) throw new InvalidActionException("The depot doesn't exist!");
        if (!checkAvailableDepot(resource)) throw new InvalidActionException("The resource can't be add in any depot, move some resources or discard it");
        depots.get(depot - 1).addResource(resource);
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
    public void removeResource(Resource resource, int depot) throws InvalidActionException {
        if (depot <= 0 || depot > depots.size()) throw new InvalidActionException("Choose a depot!");
        if (resource == null) throw new InvalidActionException("Choose a resource!");
        if (!depots.contains(depots.get(depot - 1))) throw new InvalidActionException("The depot doesn't exist!");
        if (!depots.get(depot - 1).getResources().contains(resource))
            throw new InvalidActionException("The resource you want to remove doesn't exist!");
        depots.get(depot).removeResource();
    }

    /**
     * Moves a resource from depot1 to depot2, after:
     * - Checking if the resource is not null and exists in the depot1;
     * - The depots are correct and exist;
     *
     * @param depot1
     * @param depot2
     * @throws InvalidActionException
     */
    public void moveResource(int depot1, int depot2) throws InvalidActionException {
        if (depot1 <= 0 || depot1 > depots.size()) throw new InvalidActionException("Choose the first depot!");
        if (depot2 <= 0 || depot2 > depots.size()) throw new InvalidActionException("Choose the second depot!");
        if (!depots.contains(depots.get(depot1 - 1)) || !depots.contains(depots.get(depot2 - 1)))
            throw new InvalidActionException("The depots or one of them don't exist!");
        ArrayList<Resource> movingResources = depots.get(depot1-1).getResources();
        if(movingResources.size()>depots.get(depot2-1).getSize()-depots.get(depot2-1).getResources().size()) throw new InvalidActionException("You can't move here, because there's no enough space");
        depots.get(depot1 - 1).getResources().removeAll(movingResources);
        depots.get(depot2 - 1).getResources().addAll(movingResources);
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
               if (!depots.get(i).isFull() && resource.equals(type)) {
                   return true;
               }
           }
       }
       for(int i=3;i<depots.size();i++){
           if(!depots.get(i).isFull() && resource.equals(depots.get(i).getType())){
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
    public boolean checkResourceInSomeDepot(Resource resource, int depot){
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
        for(int i=0;i<depots.size();i++){
            content.addAll(depots.get(i).getResources());
        }
        return content;
    }

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "Warehouse";
    }
}