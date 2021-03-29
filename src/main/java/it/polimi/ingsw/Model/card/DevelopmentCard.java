package it.polimi.ingsw.Model.card;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Resource;
import java.util.ArrayList;
import java.util.HashMap;

public class DevelopmentCard extends Card {

    protected Color color;
    protected int level;
    private HashMap<Integer,Resource> cost;
    private ArrayList<Resource> costProductionPower;
    private ArrayList<Resource> proceedsProductionPower;

    //constructor of the class
    public DevelopmentCard(int victoryPoints) {
        super(victoryPoints);
    }

    public Color getColor(){ return color; }
    public void setColor(Color color){ this.color=color; }

    public int getlevel(){ return level; }
    public void setLevel(int level){ this.level=level; }

    public ArrayList<Resource> getcostProductionPower(){ return costProductionPower; }
    public void setCostProductionPower(ArrayList<Resource> costProductionPower){this.costProductionPower=costProductionPower; }

    public ArrayList<Resource> getProceedsProductionPower(){ return proceedsProductionPower; }
    public void setProceedsProductionPower(ArrayList<Resource> proceedsProductionPower){this.proceedsProductionPower=proceedsProductionPower;}

    public HashMap<Integer,Resource> getCost(){return cost;}
    public void setCost(HashMap<Integer,Resource> cost){this.cost=cost;}

    //this method shows to the player the production power of the card he chose
    public ArrayList<Resource> showProductionPower(){return proceedsProductionPower;}

    /*left this method without code because sofia has to implement it with the payer*/
    public void UseProductionPower(Resource resource){}

    @Override
    public int getVictoryPoints() {
        return super.victoryPoints;
    }

    @Override
    public void setVictoryPoints(int victoryPoints) {
        super.victoryPoints=victoryPoints;
    }
}
