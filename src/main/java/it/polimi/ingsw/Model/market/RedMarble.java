package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

/*
 * SOFIA
 * */
public class RedMarble extends Marble{

    /* constructor of the class */
    public RedMarble() {
        super(Color.RED);
    }

    @Override
    public void choose(Player player) {
        //player.getGameSpace().getFaithTrack().increasePosition();
    }
}
