package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

public class ResourceManager {
    private final StrongBox strongBox;
    private final Warehouse warehouse;

    /**
     * Constructor
     * @param strongBox
     * @param warehouse
     */
    public ResourceManager(StrongBox strongBox, Warehouse warehouse) {
        this.strongBox = strongBox;
        this.warehouse = warehouse;
    }

    /**
     * Getter Method
     * @return
     */
    public StrongBox getStrongBox() {
        return strongBox;
    }

    /**
     * Getter Method
     * @return
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * It calculates Victory Points at the end of game.
     * @return
     */
    public int getVictoryPoints(){
        int numberResourceStrongBox= this.strongBox.getNumberResources();
        int numberResourceWarehouse=0;
        for(int i=0;i<this.warehouse.getDepots().size();i++){
            for(int j=0;i<this.warehouse.getDepots().get(i).getResources().size();j++) {
                numberResourceWarehouse++;
            }
        }
        return (numberResourceStrongBox+numberResourceWarehouse)/5;
    }

    public ArrayList<Resource> getResources(){
        ArrayList<Resource> resources = new ArrayList<Resource>();
        resources.addAll(strongBox.getContent());
        for(int i=0;i<warehouse.getDepots().size();i++){
            resources.addAll(warehouse.getDepots().get(i).getResources());
        }
        return resources;
    }
}
