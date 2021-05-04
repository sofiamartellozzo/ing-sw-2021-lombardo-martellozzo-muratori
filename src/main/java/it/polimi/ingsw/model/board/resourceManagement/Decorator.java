package it.polimi.ingsw.model.board.resourceManagement;

/**
 * Decorator Pattern
 * It allows to create Special Depots "to decorate" the Warehouse.
 * It contains the reference to the warehouse in the "warehouse" attribute.
 * */

public abstract class Decorator extends Warehouse{

    protected Warehouse warehouse;

}
