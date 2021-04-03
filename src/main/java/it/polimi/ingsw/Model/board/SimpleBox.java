package it.polimi.ingsw.Model.board;

/**
* GIANLUCA
 * It represents the Grey Box of the FaithTrack.
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
     * @param lastGoldBox -> The reference to the last Gold Box to obtain the Victory Points
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
     * Getter Method
     * @return
     */
    @Override
    public VaticanSection getVaticanSection() {
        return super.getVaticanSection();
    }

    /**
     * Setter Method
     * @param vaticanSection
     */
    @Override
    public void setVaticanSection(VaticanSection vaticanSection) {
        super.setVaticanSection(vaticanSection);
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
        return "SimpleBox";
    }
}
