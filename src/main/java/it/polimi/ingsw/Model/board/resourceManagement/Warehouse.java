package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.*;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 */

public abstract class Warehouse{
    protected ArrayList<Depot> depots;

    public void addResource(Resource resource, int depot) throws InvalidActionException {}

    public void removeResource(Resource resource, int depot) throws InvalidActionException {}

    public void moveResource(Resource resource, int depot1, int depot2) throws InvalidActionException {}

    public ArrayList<Depot> getDepots() {
        return depots;
    }
}