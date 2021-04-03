package it.polimi.ingsw.Model.card;
import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/* ILA */

public class DevelopmentCard extends Card {

    private final Color color;
    private final int level;
    private final ArrayList<Resource> costToBuy;
    private final ArrayList<Resource> earnProductionPower;
    private final ArrayList<Resource> costProductionPower;

    //constructor of the class

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

    //this methods show to the player the production power and the cost of the card he chose
    public ArrayList<Resource> showCostProductionPower(){return costProductionPower;}
    public ArrayList<Resource> showProceedsProductionPower(){return earnProductionPower;}

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /*in this method you remove the resouces that you paid from the Warehouse (and after from strongbox)
      and put the new ones in the StrongBox */
    public void UseProductionPower(Player player) throws InvalidActionException {

        player.getGameSpace().getResourceManager().removeResources(costProductionPower);
        player.getGameSpace().getResourceManager().addResourcesToStrongBox(earnProductionPower);
    }


}
