package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;

/**
 * Represents the FaithMarker of Lorenzo Il Magnifico.
 * As the normal FaithMarker, it indicates "position" of Lorenzo Il Magnifico
 * and has a color.
 */
public class LorenzoFaithMarker extends Resource {

    private int position;

    /**
     * Constructor
     * Set position to 0 and color to BLACK.
     */
    public LorenzoFaithMarker(){
        super(Color.BLACK);
        this.position = 0;
    }

    /**
     * Getter Method
     * @return -> The "position"
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Setter method
     * @param position -> The new position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Increase the "position" by one.
     */
    public void increasePosition() {
        this.position++;
    }
}
