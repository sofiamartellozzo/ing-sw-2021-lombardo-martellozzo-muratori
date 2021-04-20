package it.polimi.ingsw.model.card;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

/* ILA */

/**
 * this special card created with the AdditionalPower, will create a new type of card,
 * in which the player receive one faithMarker points and chooses the resource he wants
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
     * with this method the player removes the resources that he paid from the Warehouse or from StrongBox
     * and put the new one in the StrongBox, plus  the faithMarker of the player increases its position
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
