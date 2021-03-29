package it.polimi.ingsw.Model.board.resourcemanagement;

import it.polimi.ingsw.Model.Resource;

/*
* Gianluca
* It represents all type of depots
* */

public interface Depot {
    public void putResource(Resource resource);
    public void removeResource();
}
