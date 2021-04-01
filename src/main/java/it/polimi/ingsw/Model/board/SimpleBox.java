package it.polimi.ingsw.Model.board;

/**
* GIANLUCA
 * It represents the Grey Box of the FaithTrack.
 * In addition to the Box Attribute, it has the reference to
 * the lastGoldBox, to facilitate the calculation of the Victory Points
 * at the end of the game. It can be null in case of the first two boxes.
* */
public class SimpleBox extends Box{

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
     * @return
     */
    public void setVictoryPoints(GoldBox lastGoldBox) {
        if(lastGoldBox!=null) this.victoryPoints=lastGoldBox.getVictoryPoints();
        else this.victoryPoints=0;
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
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "SimpleBox";
    }
}
