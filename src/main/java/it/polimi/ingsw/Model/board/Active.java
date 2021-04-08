package it.polimi.ingsw.Model.board;

/**
 * GIANLUCA
 * State Pattern
 * One of the component of the State Pattern.
 */

public class Active implements State {

    /**
     * It sets the State attribute of the relative Pope's Favor Tile to "Active"
     * and return its Victory Points.
     * @param victoryPoints
     */
    @Override
    public int returnPoints(int victoryPoints) {
        return victoryPoints;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
