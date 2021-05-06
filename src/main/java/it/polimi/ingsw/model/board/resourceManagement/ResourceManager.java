package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

/**
 * Indicates the composition of the Warehouse and the Strongbox in the PersonalBoard
 */
public class ResourceManager {

    private final StrongBox strongBox;
    private Warehouse warehouse;

    /**
     * Constructor
     * @param strongBox -> The Strongbox where the Player puts in resources produced by the Production Power of Development Cards.
     * @param warehouse -> The Warehouse where the Player puts in all the rest of resources.
     */
    public ResourceManager(StrongBox strongBox, Warehouse warehouse) {
        this.strongBox = strongBox;
        this.warehouse = warehouse;
    }

    /**
     * Getter Method
     * @return -> The strongbox
     */
    public StrongBox getStrongBox() {
        return strongBox;
    }

    /**
     * Getter Method
     * @return -> The Warehouse
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }


    /**
     * When the game ends, calculates Victory Points:
     * counts all the resources contained by both the strongbox and the warehouse then divides by 5.
     * @return -> VictoryPoints from both the strongbox and the warehouse
     */
    public int getVictoryPoints(){
        return this.getResources().size()/5;
    }


    /**
     * In case of draw.
     * @return -> the number of all resources from both the Warehouse and the StrongBox.
     */
    public int numberAllResources(){
        return getResources().size();
    }

    /**
     * @return -> All the resources contained by both Strongbox and the Warehouse
     */
    public ArrayList<Resource> getResources(){
        ArrayList<Resource> resources = new ArrayList<>();
        resources.addAll(warehouse.getContent());
        resources.addAll(strongBox.getContent());
        return resources;
    }

    /**
     * Removes all "resources" from both Warehouse and StrongBox.
     * After checking:
     * - Both must contain all "resources"
     * @param resources -> All resources to remove
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResourcesFromBoth(ArrayList<Resource> resources) throws InvalidActionException {
        if(!resources.isEmpty()){

            if(!checkEnoughResources(resources)) throw new InvalidActionException("The warehouse or the strongbox don't contain some resources!");

            ArrayList<Resource> removeFromStrongBox = new ArrayList<>();
            for(Resource resource: resources) {
                int found = warehouse.searchResource(resource);
                if (found!=-1){
                    warehouse.removeResource(found);
                }else{
                    removeFromStrongBox.add(resource);
                }
            }

            strongBox.removeResources(removeFromStrongBox);
        }

    }

    /**
     * Add all "resources" in the strongbox.
     * @param resources -> The resources you want to put in
     */
    public void addResourcesToStrongBox(ArrayList<Resource> resources){
        try {
            strongBox.addResources(resources);
        } catch (InvalidActionException e) {
            //create a msg to notify the error
            e.printStackTrace();
        }
    }

    /**
     * Remove all "resources" from the strongbox.
     * @param resources -> All resources you want to remove
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResourcesFromStrongbox(ArrayList<Resource> resources) throws InvalidActionException {
        strongBox.removeResources(resources);
    }


    /**
     * Remove all "resources" from the Warehouse.
     * @param resources -> All the resources you want to remove
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResourcesFromWarehouse(ArrayList<Resource> resources) throws InvalidActionException {
        warehouse.removeResources(resources);
    }

    /**
     * Remove a resource from the "depot" of the warehouse
     * @param depot -> The depot where you want to remove a resource.
     * @throws InvalidActionException -> If one of the condition is not respected
     */
    public void removeResourceFromWarehouse(int depot) throws InvalidActionException {
        warehouse.removeResource(depot);
    }

    /**
     * Add a "resource" in the "depot" of the warehouse.
     * @param resource -> The resource you want to put
     * @param depot -> The depot where you want to put the resource.
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void addResourceToWarehouse(Resource resource,int depot) throws InvalidActionException {
        warehouse.addResource(resource,depot);
    }

    /**
     * Move a resource from "fromDepot" to "toDepot".
     * It can be used just in case there are AbilityDepot in the warehouse
     * and you want to move a resource from one of them or to one of them or both.
     * @param fromDepot -> The depot where you want to move from
     * @param toDepot -> The depot where you want to move to
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void moveResourceFromToAbilityDepot(int fromDepot, int toDepot) throws InvalidActionException {
        warehouse.moveResource(fromDepot,toDepot);
    }

    /**
     * Move all resources contained by "fromDepot" to "toDepot".
     * @param fromDepot -> The depot where you want to move from
     * @param toDepot -> The depot where you want to move to
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void moveResources (int fromDepot,int toDepot) throws InvalidActionException {
        warehouse.moveResources(fromDepot,toDepot);
    }

    /**
     * PRIVATE METHOD
     * Checks if both the warehouse and the strongbox contain all "resources".
     * @param resources -> The resources you want to check
     * @return -> True if contains all "resources", else false.
     * NOTE: Supports the "removeResourcesFromBoth" method.
     */
    private boolean checkEnoughResources(ArrayList<Resource> resources){
        ArrayList<Resource> typeResources = new ArrayList<>();
        typeResources.add(new Resource(Color.YELLOW));
        typeResources.add(new Resource(Color.BLUE));
        typeResources.add(new Resource(Color.PURPLE));
        typeResources.add(new Resource(Color.GREY));
        for(Resource resource: typeResources){
            if(countResource(getResources(),resource)<countResource(resources,resource)) return false;
        }
        return true;
    }

    /**
     * Counts how many resources of type "resource" there are in "content"
     * @param content -> The content where to count
     * @param resource -> The resource to count
     * @return -> How many resources of type "resource" are in "content"
     */
    public int countResource(ArrayList<Resource> content, Resource resource){
        int count = (int) content.stream().filter(r -> r.getType().equals(resource.getType())).count();
        return count;
    }

    /**
     * @return -> The content of both the warehouse and the strongbox as an ArrayList<Resource>
     */
    public ArrayList<Resource> getContent(){
        ArrayList<Resource> content= new ArrayList<>();
        for(Depot depot:getWarehouse().getDepots()){
            content.addAll(depot.getResources());
        }
        content.addAll(getStrongBox().getContent());
        return content;
    }
}