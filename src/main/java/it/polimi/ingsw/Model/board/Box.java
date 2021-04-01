package it.polimi.ingsw.Model.board;

/**
* GIANLUCA
* It's composing the FaithTrack with its extensions:
* SimpleBox, GoldBox and PopeBox.
* Attribute:
* whichSection -> If it is, indicates in which Vatican Section it's situated
* to facilitate the control of the Players, if they're in or out;
* numberBox -> the numeration of the boxes.
* */

public abstract class Box {

    protected final int whichVaticanSection;
    protected final int numberBox;
    protected int victoryPoints;

    /**
     * Constructor
     * @param whichSection
     * @param numberBox
     */
    public Box(int whichSection, int numberBox) {
        this.whichVaticanSection = whichSection;
        this.numberBox = numberBox;
        this.victoryPoints=0;
    }

    /**
     * Constructor: in case the boxes are not in a Vatican Section
     * @param numberBox
     */
    public Box(int numberBox) {
        this.whichVaticanSection=0;
        this.numberBox = numberBox;
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
