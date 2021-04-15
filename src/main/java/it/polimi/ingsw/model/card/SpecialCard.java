package it.polimi.ingsw.model.card;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

/* ILA */

/**
 * this special card generated with the AdditionalPower will create a new type of card,
 * in which the player chooses the resource he wants
 */
public class SpecialCard {

    private final ArrayList<Resource> costProductionPower;
    private ArrayList<Resource> earnProductionPower;  /* this is chosen by the player every time */

    /**
     * constructor of the class
     * @param costProductionPower
     */

    public SpecialCard(ArrayList<Resource> costProductionPower){
        this.costProductionPower = costProductionPower;
        this.earnProductionPower = null;
    }

    public ArrayList<Resource>  getCostProductionPower() {
        return costProductionPower;
    }
    public ArrayList<Resource> getProceedsProductionPower() { return earnProductionPower;}


    /**
     * in this method you remove the resource that you paid from the Warehouse or from StrongBox
     * and put the new one in the StrongBox, farther the faithMarker of the player increase its position
     * @param player
     * @param resource
     * @param where
     * @throws InvalidActionException
     */

    public void useProductionPower(Player player, Resource resource,String where) throws InvalidActionException {

        ArrayList<Resource> resourceChosen = new ArrayList<>();
        resourceChosen.add(0,resource);

        if (where.equals("Warehouse"))
        {
            player.getGameSpace().getResourceManager().removeFromWarehouse(costProductionPower);
        }
        if ( where.equals("StrongBox")) {

            player.getGameSpace().getResourceManager().removeFromStrongBox(costProductionPower);
        }

        player.getGameSpace().getResourceManager().addResourcesToStrongBox(resourceChosen);
        player.getGameSpace().getFaithTrack().increasePosition();

    }

}
