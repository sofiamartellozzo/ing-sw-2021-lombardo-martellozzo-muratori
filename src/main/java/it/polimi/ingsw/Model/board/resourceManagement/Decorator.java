package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Model.TypeResource;

/*
* GIANLUCA
* PATTERN
* It allows to create SpecialDepot "to decorate" the Warehouse.
* */

public class Decorator extends Warehouse{
    private AbilityDepot abilityDepot;

    //attributo ArrayList con due AbilityDepots

    public void createDepot(TypeResource resource){
        //crea lo SpecialDepots
    }

}
