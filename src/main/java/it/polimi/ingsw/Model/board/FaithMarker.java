package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Resource;

/*
* SOFIA
* */
public class FaithMarker extends Resource {
    private int position;
    private Color color;

    public FaithMarker() {
        super(Color.RED);
        this.position = 0;
    }

    public int getPosition(){
        return this.position;
    }

    //added fot testing
    public void setPosition(int position){this.position=position;}

    public void increasePosition(){this.position++;}
}
