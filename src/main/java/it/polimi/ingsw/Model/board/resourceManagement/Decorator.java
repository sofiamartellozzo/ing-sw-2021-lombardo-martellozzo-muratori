package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;

import java.util.ArrayList;

/**
* GIANLUCA
* Decorator Pattern
* It allows to create Special Depots "to decorate" the Warehouse.
* */

public abstract class Decorator extends Warehouse{

    private ArrayList<Depot> depots;

    /**
     * Constructor
     * this.depots refers to the Warehouse parameter depots.
     * @param warehouse -> The reference warehouse
     */
    public Decorator(Warehouse warehouse) {
        this.depots = warehouse.getDepots();
    }


    /**
     * Getter Method
     * @return -> The list of Depots
     */
    @Override
    public ArrayList<Depot> getDepots() {
        return depots;
    }

    /**
     * Create a new AbilityDepot object with the resource parameter, which
     * obliged the player to put just that type of resource in,
     * and it's numerated according to the size of the depots (ArrayList).
     * For instance, if there aren't any AbilityDepot, size is 3, creating a new AbilityDepot
     * will be 4. The same reasoning for the 5th Depot.
     * @param resource -> The resource you can put in the new created Depot (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException
     */
    public void createDepot(Resource resource) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Insert a correct resource!");
        AbilityDepot abilityDepot = new AbilityDepot(resource.getType(),depots.size()+1);
        this.depots.add(abilityDepot);
    }

}
