package it.polimi.ingsw.model.card;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.utility.AnsiColors;

import java.util.ArrayList;

/* ILA */

public class DevelopmentCard extends Card {

    private final int id;
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

    public DevelopmentCard(int id,int victoryPoints, Color color, int level, ArrayList<Resource> costToBuy, ArrayList<Resource> earnProductionPower, ArrayList<Resource> costProductionPower)
    {
        super(victoryPoints);
        this.id=id;
        this.color = color;
        this.level = level;
        this.costToBuy = costToBuy;
        this.earnProductionPower = earnProductionPower;
        this.costProductionPower = costProductionPower;
    }

    // Getter methods
    public int getId(){return id;}
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
     * when the player chose to activate the production power,
     * he removes the resources that he paid from the Warehouse of from the strongbox
     * ( the player chooses) and put the new resources received in the StrongBox
     * @param player
     * @param where
     * @throws InvalidActionException
     */
    public void useProductionPower(Player player,String where) throws InvalidActionException {


        if (where.equals("Warehouse"))
        {
            player.getGameSpace().getResourceManager().removeResourcesFromWarehouse(costProductionPower);
        }

        if (where.equals(player.getGameSpace().getResourceManager().getStrongBox().toString()))
        {

            player.getGameSpace().getResourceManager().removeResourcesFromStrongbox(costProductionPower);
        }

        /*
         * if inside the earn production power of the card is contained the Faith Marker,
         * the player doesn't have to add anything but only to move on his faith marker,
         * and add to the strongBox the other resources (if present)
         */

        for (Resource res: earnProductionPower) {
            ArrayList<Resource> toAdd = new ArrayList<>();

            if (res.getColor().equals(Color.RED))
            {
                player.getGameSpace().getFaithTrack().getFaithMarker().increasePosition();
            }
            else
            {
                toAdd.add(res);
                player.getGameSpace().getResourceManager().addResourcesToStrongBox(toAdd);
            }
        }

    }

    /**
     * method used to represent the Development card
     * @return
     */

    @Override
    public String toString() {
        return  /*"  ______________ \n" +
                "|   level:"+level+"    |\n" +
                "|   cost:"+getCostForCli()+"   |\n" +
                "|              |\n" +
                "|              |\n" +
                "|    pay:1     |\n" +
                "|    earn:1    |\n" +
                "|   vPoints:1  |\n" +
                "|______________| ";*/
        /*"DEVELOPMENT CARD: "+"      "+
        "color: "+color+"      "+
        "level: " +level+"     "+
        "cost: " +getCostForCli()+"    "+
        "pay: "+getPayForCli()+ "    "+
        "earn:"+ getEarnForCli()+ "     "+
        "vPoints: "+getVictoryPoints();*/

        "["+color+",Level:"+level+",Cost:" +getCostForCli()+",Pay:"+getPayForCli()+",Earn:"+ getEarnForCli()+",vPoints:"+AnsiColors.YELLOW_BOLD+getVictoryPoints()+AnsiColors.RESET+"]";

    }

    /*public Color getColorForCli(){
        if(color == Color.GREEN)
        {
            System.out.print(AnsiColors.GREEN_BOLD+"GREEN"+AnsiColors.RESET+",Level:"+AnsiColors.GREEN_BOLD+level+AnsiColors.RESET);
            return Color.GREEN;
        }
        throw new IllegalArgumentException("error");
    }*/
    public String getCostForCli()
    {
        String cost = "" ;
        for (Resource res:costToBuy) {

            switch (res.getType()){
                case SHIELD:
                    cost += AnsiColors.BLUE_BOLD+"\uD83D\uDD35"+AnsiColors.RESET;
                    break;
                case STONE:
                    cost += AnsiColors.BLACK_BOLD+"\u26AB"+AnsiColors.RESET;
                    break;
                case COIN:
                    cost += AnsiColors.YELLOW_BOLD+"\uD83D\uDFE1"+AnsiColors.RESET;
                    break;
                case SERVANT:
                    cost += AnsiColors.PURPLE_BOLD+"\uD83D\uDFE3"+AnsiColors.RESET;
                    break;

            }
        }
        return cost;
    }

    public String getPayForCli()
    {
        String cost = "" ;
        for (Resource res:costProductionPower) {

            switch (res.getType()){
                case SHIELD:
                    cost += AnsiColors.BLUE_BOLD+"\uD83D\uDD35"+AnsiColors.RESET;
                    break;
                case STONE:
                    cost += AnsiColors.BLACK_BOLD+"\u26AB"+AnsiColors.RESET;
                    break;
                case COIN:
                    cost += AnsiColors.YELLOW_BOLD+"\uD83D\uDFE1"+AnsiColors.RESET;
                    break;
                case SERVANT:
                    cost += AnsiColors.PURPLE_BOLD+"\uD83D\uDFE3"+AnsiColors.RESET;
                    break;

            }
        }
        return cost;
    }

    public String getEarnForCli()
    {
        String cost = "" ;
        for (Resource res:earnProductionPower) {

            switch (res.getType()){
                case SHIELD:
                    cost += AnsiColors.BLUE_BOLD+"\uD83D\uDD35"+AnsiColors.RESET;
                    break;
                case STONE:
                    cost += AnsiColors.BLACK_BOLD+"\u26AB"+AnsiColors.RESET;
                    break;
                case COIN:
                    cost += AnsiColors.YELLOW_BOLD+"\uD83D\uDFE1"+AnsiColors.RESET;
                    break;
                case SERVANT:
                    cost += AnsiColors.PURPLE_BOLD+"\uD83D\uDFE3"+AnsiColors.RESET;
                    break;

            }
        }
        return cost;
    }

    public static void main(String [] args){

        ArrayList<Resource> array = new ArrayList<>();
        array.add(0,new Resource(Color.BLUE));
        array.add(1,new Resource(Color.PURPLE));

        ArrayList<Resource> proceeds = new ArrayList<>();
        proceeds.add(0,new Resource(Color.GREY));
        proceeds.add(1,new Resource(Color.YELLOW));

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(0,new Resource(Color.BLUE));

        DevelopmentCard card1 = new DevelopmentCard(1,3,Color.GREEN,1,array,proceeds,cost);

        System.out.println(card1.toString());

    }
}



