package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

/*
 * SOFIA
 * */

/**
 * this class rapresent the specific Marble present in the Market
 * whith the color Red, so it has a different response when choose in the market
 * it doen't return a Resource but increment the position of the faith market of one
 */
public class RedMarble extends Marble{

    /* constructor of the class */
    public RedMarble() {
        this.color = Color.RED;
    }

    @Override
    public void choose(Player player) {
        player.getGameSpace().getFaithTrack().increasePosition();
    }
}
