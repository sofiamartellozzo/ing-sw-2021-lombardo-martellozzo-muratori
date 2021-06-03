package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.view.CLI.CLI;

import java.util.ArrayList;

/**
 * class used to handle the Market, and all the possible actions that a player can do
 */
public class MarketCLI {

    private CLI cli;
    private ArrayList<TypeResource> resources;
    private boolean resourceStored;
    private boolean waitMove;
    private boolean doEndMarket;

    public MarketCLI(ArrayList<TypeResource> resources, CLI cli) {
        this.resources = resources;
        this.cli = cli;
        this.resourceStored = false;
        this.waitMove = false;
        this.doEndMarket = false;
    }

    /**
     * used to wait the update of the Warehouse
     *
     * @return
     */
    public boolean isWaitMove() {
        return waitMove;
    }

    public void setWaitMove(boolean waitMove) {
        this.waitMove = waitMove;
    }

    public boolean isResourceStored() {
        return resourceStored;
    }

    /**
     * used to "wait" that the resource is stored in the warehouse
     *
     * @param resourceStored
     */
    public void setResourceStored(boolean resourceStored) {
        this.resourceStored = resourceStored;
    }

    /**
     * remove the first resource after it is put in the warehouse
     */
    public void removeResource() {
        resources.remove(0);
    }

    public int resourcesLeft() {
        return resources.size();
    }

    /**
     * manage the resources
     */
    public void handleResources() {

        if (resourceStored) {
            removeResource();
        }

        if (!waitMove && resources.size() > 0) {
            if (!resources.get(0).equals(TypeResource.BLANK)) {
                cli.buyFromMarket(resources.get(0));
            } else {
                cli.choseAndBuyFromMarket();
            }
        }

        if (resources.size() == 0 && !doEndMarket) {
            doEndMarket = true;
            cli.endMarket();
        }
    }

}

