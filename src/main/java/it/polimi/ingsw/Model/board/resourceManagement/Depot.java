package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * It represents all type of depots
 * */

public interface Depot {
    public ArrayList<Resource> getResources();
    public void putResource(Resource resource);
    public void removeResource();
}