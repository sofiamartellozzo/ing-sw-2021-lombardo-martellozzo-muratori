package it.polimi.ingsw.model.board;

/**
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
