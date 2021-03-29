package it.polimi.ingsw.Model.board;

public class VaticanSection {
    //Indicates which of the three section is
    private int which;
    //The inherent Pope's Favor Tile
    private PopesFavorTile popesFavorTile;

    //Constructor
    public VaticanSection(int which, PopesFavorTile popesFavorTile) {
        this.which = which;
        this.popesFavorTile = popesFavorTile;
    }
}
