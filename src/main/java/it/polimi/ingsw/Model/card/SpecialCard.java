package it.polimi.ingsw.Model.card;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/* ILA */
/*this special card generated with the AdditionalPower will create a new type of card,
in which the player chooses the resource he wants*/

public class SpecialCard {

    private final ArrayList<Resource> costProductionPower;
    private ArrayList <Resource> proceedsProductionPower;  /* this is choosen by the player every time*/

    //constructor of the class

    public SpecialCard(ArrayList<Resource> costProductionPower){
        this.costProductionPower=costProductionPower;
        this.proceedsProductionPower=null;
    }

    public ArrayList<Resource> getCostProductionPower() {
        return costProductionPower;
    }
    public ArrayList<Resource> getProceedsProductionPower() { return proceedsProductionPower; }

    // this is the resource that the player chooses to get
    public void setProceedsProductionPower(ArrayList<Resource> proceedsProductionPower){this.proceedsProductionPower=proceedsProductionPower;}

    //this method shows to the player the production power of the card he chose
    public ArrayList<Resource> showProductionPower(){return this.proceedsProductionPower;}

    /*left this method without code because sofia has to implement it with the player
    public void useProductionPower(Player player, Resource resource){

        FaithMarker faith= new FaithMarker(Color.RED, TypeResource.FAITHMARKER);
        faith.increasePosition();

    }*/

}
