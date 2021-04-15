package it.polimi.ingsw.model.board;

/**
 * GIANLUCA
 * TEST PASSED
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

    @Override
    public String toString() {
        return "Inactive";
    }
}
