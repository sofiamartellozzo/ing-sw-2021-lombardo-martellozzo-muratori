package it.polimi.ingsw.Model.board;

/**
 * GIANLUCA
 * TEST PASSED
 * One of the type of the boxes.
 * It has the same attribute of Box class.
 * */

public class PopeBox extends Box{

    private boolean last;
    private boolean activated;

    /**
     * Constructor
     * Last is set to false, then it can be set to true when it's necessary.
     * @param whichSection
     * @param numberBox
     */
    public PopeBox(int whichSection, int numberBox) {
        super(whichSection, numberBox);
        this.last = false;
    }

    /**
     * Getter Method
     * @return
     */
    public boolean isLast() {
        return last;
    }



    /**
     * Setter Method
     * @param last
     */
    public void setLast(boolean last) {
        this.last = last;
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
    public int getVictoryPoints(){
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
     * After checking.
     * It activates the power of the PopeBox.
     * Players in the VaticanSection call this method to activate Pope's Favor Tile.
     * */
    public void vaticanReport(VaticanSection vaticanSection){
        vaticanSection.getPopesFavorTile().setState(new Active());
    };

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "PopeBox";
    }

    /**
     * Getter Method
     * @return
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Setter Method
     * @param activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
