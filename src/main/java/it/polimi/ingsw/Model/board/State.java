package it.polimi.ingsw.Model.board;

/*
This interface with Active and Inactive Classes forms the State Pattern
in order to verify if the Pope's Favor Tile is actived.
*/

public interface State {
    //return the point of the relative Pope's Favore Tile
    public int returnPoints();
}
