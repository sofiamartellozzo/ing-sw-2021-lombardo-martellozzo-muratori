package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Model.Resource;

/*
* GIANLUCA
* It represents all type of depots
* */

public interface Depot {
    public void putResource(Resource resource);
    public void removeResource();
}
