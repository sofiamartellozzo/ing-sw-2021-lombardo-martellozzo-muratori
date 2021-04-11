package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;

import java.util.ArrayList;

/**
* GIANLUCA
 * TEST PASSED (IDENTICAL TO SPECIAL WAREHOUSE)
* Decorator Pattern
* It allows to create Special Depots "to decorate" the Warehouse.
* */

public abstract class Decorator extends Warehouse{

    protected Warehouse warehouse;

}
