package it.polimi.ingsw.model.board.resourceManagement;

/**
 * Decorator Pattern
 * It allows to create Special Depots "to decorate" the Warehouse.
 *
* */

public abstract class Decorator extends Warehouse{

    protected Warehouse warehouse;

}
