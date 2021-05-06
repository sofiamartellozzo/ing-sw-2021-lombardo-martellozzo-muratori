package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;

/**
 * Represents the Faith Marker within the FaithTrack
 * The "position" attribute indicates in which box of the FaithTrack the player is.
 * The "color" is Red because it is a type of resource.
 */
public class FaithMarker extends Resource {

    private int position;

    /**
     * Constructor
     * Set the "color" to red and the "position" to 0.
     */
    public FaithMarker() {
        super(Color.RED);
        this.position = 0;
    }

    /**
     * Getter method
     * @return -> The position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Setter Method
     * @param position -> The position you want to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Increase the position by one, so the player can go ahead in the FaithTrack.
     */
    public void increasePosition() {
        this.position++;
    }

}
