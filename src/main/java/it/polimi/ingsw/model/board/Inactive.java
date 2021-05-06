package it.polimi.ingsw.model.board;

/**
 * State Pattern
 * One of the component of the State Pattern, represent the state inactive of the Pope's Favor Tile
 */
public class Inactive implements State{

    /**
     * Returns 0 points, because the state is inactive.
     * @param victoryPoints -> The victory points which the tile should give if it was active.
     */
    @Override
    public int returnPoints(int victoryPoints) {
        return 0;
    }

    /**
     * ToString
     * @return -> "Inactive"
     */
    @Override
    public String toString() {
        return "Inactive";
    }
}
