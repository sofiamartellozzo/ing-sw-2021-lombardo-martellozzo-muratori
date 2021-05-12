package it.polimi.ingsw.model.board.resourceManagement;

import java.io.Serializable;

/**
 * Decorator Pattern
 * It allows to create Special Depots "to decorate" the Warehouse.
 * It contains the reference to the warehouse in the "warehouse" attribute.
 * */

public abstract class Decorator extends Warehouse implements Serializable {

    protected Warehouse warehouse;

}
