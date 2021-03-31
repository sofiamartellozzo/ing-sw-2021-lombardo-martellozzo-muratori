package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.TypeResource;

/**
* GIANLUCA
* PATTERN
* It allows to create Special Depots "to decorate" the Warehouse.
* */

public class Decorator extends Warehouse{
    /**
     * Create a new AbilityDepot object with the type passed in input
     * and it's numerated based on the size of the depots ArrayList.
     * So if there aren't any AbilityDepot, size is 3, creating a new AbilityDepot
     * will be 4. The same reasoning for the 5th Depot.
     * @param type (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException
     */
    public void createDepot(TypeResource type) throws InvalidActionException {
        if(type==null) throw new InvalidActionException("Insert a type of the content!");
        AbilityDepot abilityDepot = new AbilityDepot(type,depots.size()+1);
        depots.add(abilityDepot);
    }

}
