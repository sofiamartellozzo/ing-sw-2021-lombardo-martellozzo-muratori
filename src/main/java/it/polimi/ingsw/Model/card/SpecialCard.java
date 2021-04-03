package it.polimi.ingsw.Model.card;
import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/* ILA */
/*this special card generated with the AdditionalPower will create a new type of card,
in which the player chooses the resource he wants*/

public class SpecialCard {

    private final ArrayList<Resource> costProductionPower;
    private ArrayList<Resource> earnProductionPower;  /* this is chosen by the player every time*/

    /**
     * constructor of the class
     * @param costProductionPower
     */

    public SpecialCard(ArrayList<Resource>  costProductionPower){
        this.costProductionPower = costProductionPower;
        this.earnProductionPower = null;
    }

    public ArrayList<Resource>  getCostProductionPower() {
        return costProductionPower;
    }
    public ArrayList<Resource> getProceedsProductionPower() { return earnProductionPower;}


    /* in this method you remove the resource that you paid from the Warehouse
     and put the new one in the StrongBox, farther the faithMarker of the player increase its position*/
    public void useProductionPower(Player player, Resource resource) throws InvalidActionException {

        ArrayList<Resource> resourceChosen = new ArrayList<>();
        resourceChosen.add(0,resource);

        player.getGameSpace().getResourceManager().removeResources(costProductionPower);
        player.getGameSpace().getResourceManager().addResourcesToStrongBox(resourceChosen);
        player.getGameSpace().getFaithTrack().increasePosition();

    }

}
