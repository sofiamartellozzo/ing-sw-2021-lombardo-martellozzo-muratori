package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * It represents the Grey Box of the FaithTrack.
 * so the ones without victory points
* */
public class SimpleBox extends Box implements Serializable {

    /**
     * Constructor
     * @param whichSection
     * @param numberBox
     */
    public SimpleBox(int whichSection, int numberBox) {
        super(whichSection, numberBox);
    }


    /**
     * Setter Method
     * @param lastGoldBox -> The reference to the last Gold Box to obtain the Victory Points
     */
    public void setVictoryPoints(GoldBox lastGoldBox) {
        if(lastGoldBox!=null) this.victoryPoints=lastGoldBox.getVictoryPoints();
        else this.victoryPoints=0;
    }

    /**
     * Getter Method
     * @return -> victory points
     */
    @Override
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }

    /**
     * Getter Method
     * @return -> the number of the section (1-2-3)
     */
    @Override
    public int getWhichSection() {
        return super.getWhichSection();
    }

    /**
     * Getter Method
     * @return --> the number of the box
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
        return "SimpleBox";
    }
}
