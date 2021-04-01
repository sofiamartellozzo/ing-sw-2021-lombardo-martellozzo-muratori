package it.polimi.ingsw.Model.board;

/**
 * GIANLUCA
 * State Pattern
 * One of the component of the State Pattern.
 */

public class Inactive implements State{

    /**
     * It sets the State attribute of the relative Pope's Favor Tile to Inactive.
     * Returns 0 points.
     * @param victoryPoints
     */
    @Override
    public int returnPoints(int victoryPoints) {
        return 0;
    }
}
