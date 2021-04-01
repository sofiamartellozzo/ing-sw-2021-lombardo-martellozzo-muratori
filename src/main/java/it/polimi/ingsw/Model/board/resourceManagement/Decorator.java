package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;
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
     * @param resource (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException
     */
    public void createDepot(Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Insert a correct resource!");
        AbilityDepot abilityDepot = new AbilityDepot(resource.getType(),depots.size()+1);
        depots.add(abilityDepot);
    }

}
