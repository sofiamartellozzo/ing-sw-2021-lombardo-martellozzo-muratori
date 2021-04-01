package it.polimi.ingsw.Model.board;

/**
 * GIANLUCA
 * One of the type of the boxes.
 * It has the same attribute of Box class.
 * Additional attribute:
 * last -> It indicates if it is the last box of the FaithTrack,
 * so Players can play their last turn.
 * */

public class PopeBox extends Box{

    private boolean last;

    /**
    * Constructor
    * */
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
     * It actives the power of the PopeBox which verify if Players
    * are in the relative Vatican Section in order to turn (or activate) the
    * Pope's Favor Tile
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
