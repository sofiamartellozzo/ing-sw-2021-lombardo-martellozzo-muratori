package it.polimi.ingsw.Model.board;

/**
 * GIANLUCA
 * One of the type of the boxes.
 * It has the same attribute of Box class.
 * */

public class PopeBox extends Box{

    private boolean last;

    /**
     * Constructor
     * Last is set to false, then it can be set to true when it's necessary.
     * @param whichSection
     * @param numberBox
     * @param vaticanSection
     */
    public PopeBox(int whichSection, int numberBox, VaticanSection vaticanSection) {
        super(whichSection, numberBox);
        this.vaticanSection = vaticanSection;
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
     * It actives the power of the PopeBox which verify if Players
    * are in the relative Vatican Section in order to turn (or activate) the
    * Pope's Favor Tile.
     * Maybe goes to the Control.
    * */
    public void powerSPL(){
        //It must be implemented by the Controller
    };

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "PopeBox";
    }
}
