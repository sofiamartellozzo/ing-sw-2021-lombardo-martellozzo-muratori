package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * Indicates the composition of the Warehouse and the Strongbox.
 */
public class ResourceManager {

    private final StrongBox strongBox;
    private Warehouse warehouse;

    /**
     * Constructor
     * @param strongBox -> The Strongbox where the Player puts in resources produced by the Production Power of Development Cards
     * @param warehouseStandard -> The Warehouse where the Player puts in all the rest of resources
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
     * In case of draw, returns the number of all resources from both the Warehouse and the StrongBox.
     * @return
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
     * //CHANGE NAME -> removeResourcesFromBoth
     * Removes a list of resources from both warehouse and strongbox.
     * @param resources
     */
    public void removeResources(ArrayList<Resource> resources) throws InvalidActionException {
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
     * Adds resources to the StrongBox.
     * @param resources
     */
    public void addResourcesToStrongBox(ArrayList<Resource> resources){
        strongBox.addResources(resources);
    }

    /**
     * Change Name -> removeResourcesFromStrongBox
     * Remove a list of resources from the strongbox.
     * @param resources
     */
    public void removeFromStrongBox(ArrayList<Resource> resources) throws InvalidActionException {
        strongBox.removeResources(resources);
    }


    /**
     * Change name -> removeResourcesFromWarehouse
     * Remove a list of resources from the warehouse.
     * @param resources
     * @throws InvalidActionException
     */
    public void removeFromWarehouse(ArrayList<Resource> resources) throws InvalidActionException {
        warehouse.removeResources(resources);
    }


    public void removeResourceFromWarehouse(int depot) throws InvalidActionException {
        warehouse.removeResource(depot);
    }

    public void addResourceToWarehouse(Resource resource,int depot) throws InvalidActionException {
        warehouse.addResource(resource,depot);
    }

    public void moveResourceToAbilityDepot(int fromDepot,int toDepot) throws InvalidActionException {
        warehouse.moveResource(fromDepot,toDepot);
    }

    public void moveResources (int fromDepot,int toDepot) throws InvalidActionException {
        warehouse.moveResources(fromDepot,toDepot);
    }



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
     * Counts from "resources" the resource you want to count
     * @param resources
     * @param resource
     * @return
     */
    private int countResource(ArrayList<Resource> resources, Resource resource){
        int count = (int) resources.stream().filter(r -> r.getType().equals(resource.getType())).count();
        return count;
    }


}