package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
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
    public ResourceManager(StrongBox strongBox, WarehouseStandard warehouseStandard) {
        this.strongBox = strongBox;
        this.warehouse = warehouseStandard;
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
     * @return -> All the resources contained by both Strongbox and the Warehouse
     */
    public ArrayList<Resource> getResources(){
        ArrayList<Resource> resources = new ArrayList<>();
        resources.addAll(strongBox.getContent());
        resources.addAll(warehouse.getContent());
        return resources;
    }

    /**
     * Setter Method
     * In case the decorator activates
     * @param specialWarehouse -> The new Warehouse with the new depot(s) created.
     */
    public void setWarehouseStandard(SpecialWarehouse specialWarehouse) {
        this.warehouse = specialWarehouse;
    }

    /**
     * Removes a list of resources from both warehouse and strongbox.
     * @param resources
     */
    public void removeResources(ArrayList<Resource> resources) throws InvalidActionException {
        if(!resources.isEmpty()) {
            removeFromWarehouse(resources);
        }
        if(!resources.isEmpty()) {
            removeFromStrongBox(resources);
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
     * Remove a list of resources from the strongbox.
     * @param resources
     */
    public void removeFromStrongBox(ArrayList<Resource> resources){
        if(!resources.isEmpty() && getWarehouse().getContent().containsAll(resources)){
            getStrongBox().getContent().removeAll(resources);
        }
    }

    /**
     * Remove a list of resources from the warehouse.
     * @param resources
     * @throws InvalidActionException
     */
    public void removeFromWarehouse(ArrayList<Resource> resources) throws InvalidActionException {
        for(Depot depot: getWarehouse().getDepots()){
            for(Resource resource: depot.getResources()){
                if(resources.contains(resource)){
                  resources.remove(resource);
                  depot.removeResource();
                }
            }
        }
    }



}