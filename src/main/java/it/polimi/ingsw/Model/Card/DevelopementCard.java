package it.polimi.ingsw.Model.Card;
import java.util.HashMap;

public class DevelopementCard extends Card {

    protected Color color;
    protected int level;
    private HashMap<Integer,Resource> cost;
    private Resource[] costProductionPower;
    private Resource[] proceedsProductionPower;

    public Color getColor(){ return color; }
    public void setColor(Color color){ this.color=color; }

    public int getlevel(){ return level; }
    public void setLevel(int level){ this.level=level; }

    public Resource[] getcostProductionPower(){ return costProductionPower; }
    public void setCostProductionPower(Resource[] costProductionPower){this.costProductionPower=costProductionPower; }

    public Resource[] getProceedsProductionPower(){ return proceedsProductionPower; }
    public void setProceedsProductionPower(Resource[] proceedsProductionPower){this.proceedsProductionPower=proceedsProductionPower;}

    public HashMap<Integer,Resource> getCost(){return cost;}
    public void setCost(HashMap<Integer,Resource> cost){this.cost=cost;}

    public Resource[] showProductionPower(){return proceedsProductionPower;}
    public void UseProductionPower(){};

    @Override
    public int getVicroyPoints() {
        return super.VictoryPoints;
    }

    @Override
    public void setVictoryPoints(int VictoryPoints) {
        super.VictoryPoints=VictoryPoints;
    }
}
