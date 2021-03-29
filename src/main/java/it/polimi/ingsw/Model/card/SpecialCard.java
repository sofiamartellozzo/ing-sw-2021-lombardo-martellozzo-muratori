package it.polimi.ingsw.Model.card;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/*this special card generated with the AdditionalPower will create a new type of card,
in which the player chooses the resource he wants*/

public class SpecialCard  {

    private ArrayList<Resource> costProductionPower;
    private ArrayList<Resource> proceedsProductionPower;

    //constructor of the class
    public SpecialCard(ArrayList<Resource> costProductionPower,ArrayList<Resource> proceedsProductionPower){
        this.proceedsProductionPower=proceedsProductionPower;
        this.costProductionPower=costProductionPower;
    }

    public ArrayList<Resource> getCostProductionPower() {
        return costProductionPower;
    }

    public void setCostProductionPower(ArrayList<Resource> costProductionPower) {
        this.costProductionPower = costProductionPower;
    }

    public ArrayList<Resource> getProceedsProductionPower() {
        return proceedsProductionPower;
    }

    public void setProceedsProductionPower(ArrayList<Resource> proceedsProductionPower) {
        this.proceedsProductionPower = proceedsProductionPower;
    }

    //this method shows to the player the production power of the card he chose
    public ArrayList<Resource>showProductionPower(){return this.proceedsProductionPower;}

    /*left this method without code because sofia has to implement it with the payer*/
    public void useProductionPower(){

        /*FaithMarker faith= new FaithMarker(Color.RED, TypeResource.FAITHMARKER);
        faith.increasePosition();*/
    }
}
