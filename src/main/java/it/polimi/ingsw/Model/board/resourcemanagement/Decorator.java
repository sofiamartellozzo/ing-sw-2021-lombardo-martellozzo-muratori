package it.polimi.ingsw.Model.board.resourcemanagement;

import it.polimi.ingsw.Model.TypeResource;

/*
* Gianluca
* PATTERN
* It allows to create SpecialDepot "to decorate" the Warehouse.
* */

public class Decorator extends Warehouse{
    private AbilityDepot abilityDepot;

    public void createDepot(TypeResource resource){};

}
