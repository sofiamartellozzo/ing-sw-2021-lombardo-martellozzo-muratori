package it.polimi.ingsw.model.card;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

/* ILA */

public class DevelopmentCard extends Card {

    private final Color color;
    private final int level;
    private final ArrayList<Resource> costToBuy;
    private final ArrayList<Resource> earnProductionPower;
    private final ArrayList<Resource> costProductionPower;

    /**
     * constructor of the class
     * @param victoryPoints
     * @param color
     * @param level
     * @param costToBuy
     * @param earnProductionPower
     * @param costProductionPower
     */

    public DevelopmentCard(int victoryPoints, Color color, int level, ArrayList<Resource> costToBuy, ArrayList<Resource> earnProductionPower, ArrayList<Resource> costProductionPower)
    {
        super(victoryPoints);
        this.color = color;
        this.level = level;
        this.costToBuy = costToBuy;
        this.earnProductionPower = earnProductionPower;
        this.costProductionPower = costProductionPower;
    }

    // Getter methods
    public Color getColor(){ return color; }
    public int getlevel(){ return level; }
    public ArrayList<Resource> getCost(){ return costToBuy;}

    /**
     * this methods show to the player the earn production power and the cost of the card he chose
     * @return
     */
    public ArrayList<Resource> showCostProductionPower(){return costProductionPower;}
    public ArrayList<Resource> showProceedsProductionPower(){return earnProductionPower;}

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * in this method you remove the resouces that you paid from the Warehouse (and after from strongbox) but you can't do both
     * and put the new ones in the StrongBox
     * @param player
     * @param where
     * @throws InvalidActionException
     */
    public void useProductionPower(Player player,String where) throws InvalidActionException {


        if (where.equals("Warehouse"))
        {
            player.getGameSpace().getResourceManager().removeFromWarehouse(costProductionPower);
        }
        if (where.equals("StrongBox")) {

            player.getGameSpace().getResourceManager().removeFromStrongBox(costProductionPower);
        }
        player.getGameSpace().getResourceManager().addResourcesToStrongBox(earnProductionPower);

        //if the resource is a faithMarker I don't have to add nothing!! Only do 1 step on !!!!

    }


}
