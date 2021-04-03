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
        for(int i=0;i<warehouse.getDepots().size();i++){
            resources.addAll(warehouse.getDepots().get(i).getResources());
        }
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
     * Removes a list of resources.
     * First, checks if in all Depots of the Warehouse there are some resources you want to remove,
     * if yes, it removes them, removing also in the resources parameter.
     * Second, it tries to remove the rest of resources from the StrongBox.
     * @param resources
     */
    public void removeResources(ArrayList<Resource> resources) throws InvalidActionException {

        for (int i = 0; i < warehouse.getDepots().size(); i++){
            if(!resources.isEmpty()) {
                Depot depot = warehouse.getDepots().get(i);
                for (int j = 0; j < depot.getResources().size(); j++) {
                    if (!resources.isEmpty() && resources.contains(depot.getResources().get(j))){
                        resources.remove(depot.getResources().get(j));
                        depot.removeResource();
                    }else{
                        break;
                    }
                }
            }else{
                break;
            }
        }

        if(!resources.isEmpty()) strongBox.removeResources(resources);
    }

    /**
     * Adds resources to the StrongBox.
     * @param resources
     */
    public void addResourcesToStrongBox(ArrayList<Resource> resources){
        strongBox.addResources(resources);
    }

}
