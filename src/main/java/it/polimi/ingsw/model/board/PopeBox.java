package it.polimi.ingsw.model.board;

/**
 * One of the type of the boxes.
 * It has the same attribute of Box class.
 * The "last" attribute indicates if it is the last box of the FaithTrack.
 * The "activated" attribute indicates if it was already activated.
 * */
public class PopeBox extends Box{

    private boolean last;
    private boolean activated;

    /**
     * Constructor
     * Last and activated are set to false, then it can be set to true when it's necessary.
     * @param whichSection -> In which section the box is owned
     * @param numberBox -> The numeration
     */
    public PopeBox(int whichSection, int numberBox) {
        super(whichSection, numberBox);
        this.last = false;
        this.activated=false;
    }

    /**
     * Constructor
     * For the last PopeBox of the FaithTrack.
     * @param whichSection -> In which section the box is owned
     * @param numberBox -> The numeration
     * @param victoryPoints -> The victory points
     */
    public PopeBox(int whichSection,int numberBox, int victoryPoints){
        super(whichSection,numberBox);
        this.last=true;
        this.activated=false;
        this.victoryPoints=victoryPoints;
    }

    /**
     * Getter Method
     * @return -> "last"
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Setter Method
     * In case it was activated.
     * @param last -> The new state
     */
    public void setLast(boolean last) {
        this.last = last;
    }

    /**
     * Setter Method
     * In case the victory points depends on the last gold box.
     * @param lastGoldBox -> The reference to the last gold box
     */
    public void setVictoryPoints(GoldBox lastGoldBox) {
        if(lastGoldBox!=null) this.victoryPoints=lastGoldBox.getVictoryPoints();
        else this.victoryPoints=0;
    }

    /**
     * Getter Method
     * @return -> The Victory Points
     */
    @Override
    public int getVictoryPoints(){
        return super.getVictoryPoints();
    }

    /**
     * Getter Method
     * @return -> The Vatican Section
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
     * To use only after checking the player.
     * It activates the power of the PopeBox.
     * Players in the VaticanSection call this method to activate Pope's Favor Tile.
     * If the player is in the vatican section set the relative pope's favor tile to active.
     * */
    public void vaticanReport(VaticanSection vaticanSection){
        vaticanSection.getPopesFavorTile().setState(new Active());
    }

    /**
     * toString Method
     * @return -> "PopeBox"
     */
    @Override
    public String toString() {
        return "PopeBox";
    }

    /**
     * Getter Method
     * @return -> If it is activated or not.
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Setter Method
     * @param activated -> The new state of activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
