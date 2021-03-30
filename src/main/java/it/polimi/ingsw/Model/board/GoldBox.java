package it.polimi.ingsw.Model.board;

/**
* GIANLUCA
* One of the type of the boxes.
* It has the same attribute of Box class.
* Additional attribute:
* victoryPoints -> The value of Victory Points, to calculate the final score.
* */

public class GoldBox extends Box{
    private final int victoryPoints;

    /**
     * Constructor
     * @param whichSection
     * @param numberBox
     * @param victoryPoints
     */
    public GoldBox(int whichSection, int numberBox, int victoryPoints) {
        super(whichSection,numberBox);
        this.victoryPoints = victoryPoints;
    }

    /**
     * Constructor: in case the Gold Boxes are not in a vatican section
     * @param numberBox
     * @param victoryPoints
     */
    public GoldBox(int numberBox, int victoryPoints) {
        super(numberBox);
        this.victoryPoints = victoryPoints;
    }

    /**
     * Getter Method
     * @return
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }
}
