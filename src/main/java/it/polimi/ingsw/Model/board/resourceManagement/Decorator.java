package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Model.TypeResource;

/**
* GIANLUCA
* PATTERN
* It allows to create SpecialDepot "to decorate" the Warehouse.
* */

public class Decorator extends Warehouse{

    public void createDepot(TypeResource type,int num){
        AbilityDepot abilityDepot = new AbilityDepot(type,num);
        depots.add(abilityDepot);
    }

}
