package it.polimi.ingsw.Model.board;

/**
 * GIANLUCA
 * STATE PATTERN
 * It indicates if the Pope's Favor Tile is active or inactive,
 * in case is active return its VictoryPoints.
 */
public interface State {
    /**
     * @return
     */
    public int returnPoints(int victoryPoints);
}
