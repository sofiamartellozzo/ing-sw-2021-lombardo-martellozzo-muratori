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
    * Getter methods
    * */
    public boolean isLast() {
        return last;
    }

    /**
    * Setter methods
    * */
    public void setLast(boolean last) {
        this.last = last;
    }

    /**
     * It actives the power of the PopeBox which verify if Players
    * are in the relative Vatican Section in order to turn (or activate) the
    * Pope's Favor Tile
    * */
    public void powerSPL(){
    };

}
