package it.polimi.ingsw.Model.board;

/**
* GIANLUCA
 * TEST PASSED
* It's composing the FaithTrack with its extensions: SimpleBox, GoldBox and PopeBox.
* */

public abstract class Box {

    protected final int whichVaticanSection;
    protected final int numberBox;
    protected int victoryPoints;

    /**
     * Constructor
     * Victory Points are set to 0, then if it's necessary, it can be set.
     * Same for the Vatican Section which is null and then it can be set.
     * @param whichSection -> Indicates in which Vatican Section it's situated (1st, 2nd, 3rd)
     * @param numberBox -> The numeration of the boxes
     */
    public Box(int whichSection, int numberBox) {
        this.whichVaticanSection = whichSection;
        this.numberBox = numberBox;
        this.victoryPoints=0;
    }


    /**
     * Constructor
     * In case the boxes are not in a Vatican Section
     * @param numberBox
     */
    public Box(int numberBox) {
        this.whichVaticanSection=0;
        this.numberBox = numberBox;
        this.victoryPoints=0;
    }

    /**
     * Getter Method
     * @return
     */
    public int getWhichSection() {
        return whichVaticanSection;
    }

    /**
     * Getter Method
     * @return
     */
    public int getNumberBox() {
        return numberBox;
    }

    /**
     * Getter Method
     * @return
     */
    public int getVictoryPoints(){return victoryPoints;}

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "Box";
    }
}
