package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;

import java.io.Serializable;

/*
SOFI
 */

/**
 * this class rapresent Lorenzo Il Magnifico
 * this is like the faith marker, the differences are that it is BLACK (not red), rapresent Lorenzo il Magnifico
 * himself to wich the player plays against only in single Mode
 * it is stored in the (Solo) Personal Board of the player
 */

public class LorenzoFaithMarker extends Resource implements Serializable {

    private int position;  //the position in the faith track
    private Color color;   //it has to be BLACK

    /* constructor */
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
        System.out.println("Increase Lorenzo");
        if (position<24){
        this.position++;}
    }
}
