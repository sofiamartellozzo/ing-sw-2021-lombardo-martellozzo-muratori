package it.polimi.ingsw.model.board;

/**
* GIANLUCA
 * TEST PASSED
* One of the type of the boxes.
* It has the same attribute of Box class.
* */

public class GoldBox extends Box{

    /**
     * Constructor
     * @param whichSection
     * @param numberBox
     * @param victoryPoints -> The value of Victory Points, to calculate the final score.
     */
    public GoldBox(int whichSection, int numberBox, int victoryPoints) {
        super(whichSection,numberBox);
        this.victoryPoints = victoryPoints;
    }

    /**
     * Constructor
     * In case the Gold Boxes are not in a vatican section
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
    @Override
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }

    /**
     * Getter Method
     * @return
     */
    @Override
    public int getWhichSection() {
        return super.getWhichSection();
    }

    /**
     * Getter Method
     * @return
     */
    @Override
    public int getNumberBox() {
        return super.getNumberBox();
    }

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "GoldBox";
    }
}
