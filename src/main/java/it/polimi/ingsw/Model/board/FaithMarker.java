package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.Color;

/*
* SOFIA
* */
public class FaithMarker {
    private int position;
    private Color color;

    public FaithMarker() {
        this.color = Color.RED;
        this.position = 0;
    }

    public int getPosition(){
        return this.position;
    }

    //added fot testing
    public void setPosition(int position){this.position=position;}

    public void increasePosition(){this.position++;}
}
