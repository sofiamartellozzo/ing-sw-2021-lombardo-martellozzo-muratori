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
     * @param popesFavorTile
     */
    @Override
    public int returnPoints(PopesFavorTile popesFavorTile) {
        return popesFavorTile.getVictoryPoints();
    }
}
