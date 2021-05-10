package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;

import java.io.Serializable;

/*
 * SOFIA
 * */

/**
 * this class rapresent the faith Marker, this is a "type of resource" because have the color (red)
 * and the type, but is only one for each player and track the position of the player himself in
 * his own faith track
 */
public class FaithMarker extends Resource implements Serializable {

    private int position;   //the position on the faith track
    private Color color;    //it has to be RED

    /* constructor */
    public FaithMarker() {
        super(Color.RED);
        this.position = 0;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * moving the marker of +1 in the faith track
     */
    public void increasePosition() {
        this.position++;
    }
}
