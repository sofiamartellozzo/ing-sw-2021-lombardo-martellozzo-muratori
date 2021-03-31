package it.polimi.ingsw.Model.board;

/**
* GIANLUCA
 * It represents the Grey Box of the FaithTrack.
 * In addition to the Box Attribute, it has the reference to
 * the lastGoldBox, to facilitate the calculation of the Victory Points
 * at the end of the game. It can be null in case of the first two boxes.
* */
public class SimpleBox extends Box{

    private final int lastGoldBoxVictoryPoints;

    /**
     * Constructor
     * @param whichSection
     * @param numberBox
     * @param lastGoldBoxVictoryPoints
     */
    public SimpleBox(int whichSection, int numberBox, int lastGoldBoxVictoryPoints) {
        super(whichSection, numberBox);
        this.lastGoldBoxVictoryPoints=lastGoldBoxVictoryPoints;
    }

    /**
     * Getter Method
     * @return
     */
    public int getLastGoldBoxVictoryPoints() {
        return lastGoldBoxVictoryPoints;
    }

    /**
     * New method, first two boxes return 0 points and other boxes return the victory points of the last gold box
     */
}
