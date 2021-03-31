package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * It represents all type of depots
 * */

public interface Depot {
    public ArrayList<Resource> getResources();
    public void putResource(Resource resource) throws InvalidActionException;
    public void removeResource() throws InvalidActionException;
}