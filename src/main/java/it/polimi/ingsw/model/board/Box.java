package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * It's composing the FaithTrack with its extensions: SimpleBox, GoldBox and PopeBox.
 * The "whichVaticanSection" attribute indicates which Vatican Section contains it (0 if it's not contained by no vatican section)
 * The "numberBox" attribute refers to the numeration.
 * The "victoryPoints" attribute indicates how many victory points gives the box.
 * */
public abstract class Box implements Serializable {

    protected final int whichVaticanSection;
    protected final int numberBox;
    protected int victoryPoints;

    /**
     * Constructor
     * Victory Points are set to 0, then if it's necessary, it can be set.
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
     * In case the box is not in a Vatican Section.
     * @param numberBox ->The numeration of the boxes
     */
    public Box(int numberBox) {
        this.whichVaticanSection=0;
        this.numberBox = numberBox;
        this.victoryPoints=0;
    }

    /**
     * Getter Method
     * @return -> "whichVaticanSection" attribute
     */
    public int getWhichSection() {
        return whichVaticanSection;
    }

    /**
     * Getter Method
     * @return -> "numberBox" attribute
     */
    public int getNumberBox() {
        return numberBox;
    }

    /**
     * Getter Method
     * @return -> "victoryPoints" attribute
     */
    public int getVictoryPoints(){return victoryPoints;}

    /**
     * toString Method
     * @return -> "Box"
     */
    @Override
    public String toString() {
        return "Box";
    }
}
