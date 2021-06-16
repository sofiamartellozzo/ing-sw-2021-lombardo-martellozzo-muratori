package it.polimi.ingsw.model.card;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.utility.AnsiColors;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * cards that are in the Dev Table
 * each card has a specific cost to buy (resources to pay to buy the card),
 * earnProductionPower (resources obtained after activating it) ,costProductionPower (resources to pay to activate it)
 */
public class DevelopmentCard extends Card implements Serializable {

    private final int id;
    private final Color color;
    private final int level;
    private final ArrayList<Resource> costToBuy;
    private final ArrayList<Resource> earnProductionPower;
    private final ArrayList<Resource> costProductionPower;

    /**
     * constructor of the class
     * @param victoryPoints --> points of each card
     * @param color --> specific color of the card
     * @param level --> level of te card
     * @param costToBuy --> resources to pay to buy the card
     * @param earnProductionPower --> resources obtained after activating it
     * @param costProductionPower --> resources to pay to activate it
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
     * (the player chooses) and put the new resources received in the StrongBox
     * @param player --> player
     * @param where --> place from where taking the resources
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
        return "┌------------------------┐ \n" +

                " ID: "   +id +"        \n"+
                " color: "+color+"        \n"+
                " level: " + level + "     \n" +
                " cost: " + getCostForCli() + "  \n" +
                " pay: "+getPayForCli()+"    \n" +
                " earn: "+getEarnForCli()+" \n" +
                " vp: "+AnsiColors.YELLOW_BOLD+getVictoryPoints()+AnsiColors.RESET+"  \n" +

                "└-----------------------┘\n";

    }

    public String getIDtoString(){ return " ID: "+id+"                      ";}
    public String getColortoString(){
        return " color: "+color+"               ";
    }
    public String getleveltostring(){
        return " level: "+level+"                   ";
    }
    public String getcosttostring(){
        return " cost: "+getCostForCli();
    }
    public String getpaytostring(){
        return " pay: "+getPayForCli();
    }
    public String getearntostring(){
        return " earn: "+getEarnForCli();
    }
    public String getvictorytostring(){ return " vp: "+AnsiColors.YELLOW_BOLD+getVictoryPoints()+AnsiColors.RESET+"                      "; }


/*-------------------------------------------------------------------------------------------------------------------------*/
                                  //METHODS TO CREATE THE RESOURCES

    /**
     * private method used to calibrate te distance between two string depending on the lenght of the cost
     * @param lenght
     * @return
     */
    private String countSpaces(int lenght){
        if(lenght == 1){
            return "                    ";
        } else if(lenght == 2){
            return "                  ";
        }else if(lenght == 3){
            return "                ";
        }else if(lenght == 4){
            return "              ";
        }else if(lenght == 5){
            return "              ";
        }else if(lenght == 6){
            return "           ";
        }else if(lenght == 7){
            return "      ";
        }else{
            return "   ";
        }
    }

    public String getCostForCli()
    {
        String cost = "" ;
        int lenght = 0;
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
                case FAITHMARKER:
                    cost += AnsiColors.RED_BOLD+"\uD83D\uDD34"+AnsiColors.RESET;
                    break;

            }
            lenght++;
        }
        return cost+countSpaces(lenght);
    }

    /**
     * used to convert the cost to pay in emoji
     * @return
     */
    public String getPayForCli()
    {
        String cost = "" ;
        int lenght = 0;    //this variable count the length of the cost
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
                case FAITHMARKER:
                    cost += AnsiColors.RED_BOLD+"\uD83D\uDD34"+AnsiColors.RESET;
                    break;

            }
            lenght++;
        }
        return cost+countSpaces(lenght);
    }

    /**
     * used to convert the earned resources in emoji
     * @return
     */
    public String getEarnForCli()
    {
        String cost = "" ;
        int lenght = 0;
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
                case FAITHMARKER:
                    cost += AnsiColors.RED_BOLD+"\uD83D\uDD34"+AnsiColors.RESET;
                    break;

            }
            lenght++;
        }
        return cost+countSpaces(lenght);
    }

}



