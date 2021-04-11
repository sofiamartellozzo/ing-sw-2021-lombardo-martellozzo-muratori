package it.polimi.ingsw.Model.board;

/**
 * GIANLUCA
 * TEST PASSED
 * State Pattern
 * It indicates if the Pope's Favor Tile is active or inactive,
 * in case is active return its VictoryPoints.
 */
public interface State {
    /**
     * @return
     */
    int returnPoints(int victoryPoints);

    @Override
    String toString();
}
