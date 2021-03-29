package it.polimi.ingsw.Model.board;

public class PopesFavorTile {
    // Indicates which of the three Pope's Favore Tile that we refer
    private int position;
    // The value of Victory Points
    private int victoryPoints;
    // Attribute of the type State Pattern to know if active/inactive,
    // useful to know which of Tiles we have to consider
    // for calculating Victory Points at the end of the game
    private State state;

    // Constructor:
    // The "state" attribute is set to "Inactive", when the game starts
    public PopesFavorTile(int position, int victoryPoints, State state) {
        this.position = position;
        this.victoryPoints = victoryPoints;
        this.state=state;
    }
}
