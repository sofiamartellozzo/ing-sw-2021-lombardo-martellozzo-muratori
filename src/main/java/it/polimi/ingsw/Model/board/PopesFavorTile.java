package it.polimi.ingsw.Model.board;

/**
 * GIANLUCA
 * It represents the Pope's Favor Tile.
 * Attribute:
 * whichVaticanSection -> If it is the 1st, 2nd or 3rd Tile of the relative section
 * victoryPoints -> It contains the value of Victory Points.
 * state -> It indicates if it "active" or "inactive", using the State Pattern.
 */
public class PopesFavorTile {
    private final int whichVaticanSection;
    private final int victoryPoints;
    private State state;

    /**
     * Constructor
     * @param whichVaticanSection
     * @param victoryPoints
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
     * @return
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Getter Method
     * @return
     */
    public State getState() {
        return state;
    }

    /**
     * Setter Method for the State Pattern
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }
}
