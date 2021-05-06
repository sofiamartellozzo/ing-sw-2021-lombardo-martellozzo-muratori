package it.polimi.ingsw.model.board;


/**
 * One of the three type of boxes.
 */
public class GoldBox extends Box{

    /**
     * Constructor
     * @param whichSection -> In which section is
     * @param numberBox -> The numeration
     * @param victoryPoints -> The value of Victory Points, to calculate the final score.
     */
    public GoldBox(int whichSection, int numberBox, int victoryPoints) {
        super(whichSection,numberBox);
        this.victoryPoints = victoryPoints;
    }

    /**
     * Constructor
     * In case the Gold Box is not in a vatican section
     * @param numberBox -> The numeration
     * @param victoryPoints -> The value of Victory Points, to calculate the final score.
     */
    public GoldBox(int numberBox, int victoryPoints) {
        super(numberBox);
        this.victoryPoints = victoryPoints;
    }

    /**
     * Getter Method
     * @return -> The victory points
     */
    @Override
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }

    /**
     * Getter Method
     * @return -> The section where the box is
     */
    @Override
    public int getWhichSection() {
        return super.getWhichSection();
    }

    /**
     * Getter Method
     * @return -> The numeration
     */
    @Override
    public int getNumberBox() {
        return super.getNumberBox();
    }

    /**
     * toString Method
     * @return -> "GoldBox"
     */
    @Override
    public String toString() {
        return "GoldBox";
    }
}
