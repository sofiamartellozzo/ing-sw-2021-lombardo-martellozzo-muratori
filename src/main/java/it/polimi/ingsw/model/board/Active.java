package it.polimi.ingsw.model.board;

/**
 * State Pattern
 * One of the component of the State Pattern.
 */

public class Active implements State {

    /**
     * It sets the State attribute of the relative Pope's Favor Tile to "Active"
     * and return its Victory Points.
     * @param victoryPoints -> The value of Victory Points
     */
    @Override
    public int returnPoints(int victoryPoints) {
        return victoryPoints;
    }

    /**
     * ToString Method
     * @return -> "Active"
     */
    @Override
    public String toString() {
        return "Active";
    }
}
