package it.polimi.ingsw.Model.card;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/* ILA */

public class DevelopmentCard extends Card {

    private final Color color;
    private final int level;
    private final HashMap<Integer,Resource> cost;
    private final ArrayList<Resource> proceedsProductionPower;
    private final ArrayList<Resource> costProductionPower;

    //constructor of the class

    public DevelopmentCard(int victoryPoints, Color color, int level, HashMap<Integer, Resource> cost, ArrayList<Resource> proceedsProductionPower, ArrayList<Resource> costProductionPower)
    {
        super(victoryPoints);
        this.color=color;
        this.level=level;
        this.cost=cost;
        this.proceedsProductionPower = proceedsProductionPower;
        this.costProductionPower = costProductionPower;
    }

    // Getter methods

    public Color getColor(){ return color; }
    public int getlevel(){ return level; }
    public HashMap<Integer,Resource> getCost(){return cost;}

    //this methods show to the player the production power and the cost of the card he chose
    public ArrayList<Resource> showCostProductionPower(){return costProductionPower;}
    public ArrayList<Resource> showProceedsProductionPower(){return proceedsProductionPower;}

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /*left this method without code because sofia has to implement it with the payer
    public void UseProductionPower(Player player){}*/


}
