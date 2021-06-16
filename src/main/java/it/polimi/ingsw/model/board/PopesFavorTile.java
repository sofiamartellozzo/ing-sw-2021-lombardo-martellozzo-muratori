package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * It represents the Pope's Favor Tile.
 * A small card that can be activated or not, if it is active you earn the victory points of that pope,
 * otherwise you discard them
 */
public class PopesFavorTile implements Serializable {
    private final int whichVaticanSection;
    private final int victoryPoints;
    private State state;

    /**
     * Constructor
     * State is set to "Inactive".
     * @param whichVaticanSection -> If it is the 1st, 2nd or 3rd Tile of the relative section
     * @param victoryPoints -> It contains the value of Victory Points.
     */
    public PopesFavorTile(int whichVaticanSection, int victoryPoints) {
        this.whichVaticanSection = whichVaticanSection;
        this.victoryPoints = victoryPoints;
        this.state= new Inactive();
    }

    /**
     * Getter Method
     * @return
     */
    public int getWhichVaticanSection() {
        return whichVaticanSection;
    }

    /**
     * Getter Method
     * @return -> Return Victory Points based on the state attribute
     */
    public int getVictoryPoints() {
        return this.state.returnPoints(this.victoryPoints);
    }

    /**
     * Getter Method
     * @return
     */
    public State getState() {
        return state;
    }

    /**
     * Setter Method
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }
}
