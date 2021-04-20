package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

/*
* SOFIA*/

/**
 * this class rapresent a normal Marble present in the Market
 * it is rapresented by a color, that color rapresent the Resource that relese
 * when the plyer buy from the Market
 */
public class ColoredMarble extends Marble {

    /* constructor of the class */
    public ColoredMarble(Color color){
            this.color = color;
    }

    @Override
    public void choose(Player player) throws InvalidActionException {
        if (this.getColor() != Color.WHITE){
            Resource resource = new Resource(this.getColor());
            player.putResources(resource);
        }
        else if (this.getColor() == Color.WHITE){
            if(player.getWhiteSpecialMarble() != null){
                //call the special ability of the special Marble
                player.getWhiteSpecialMarble().choose(player);
            }
        }
    }
}