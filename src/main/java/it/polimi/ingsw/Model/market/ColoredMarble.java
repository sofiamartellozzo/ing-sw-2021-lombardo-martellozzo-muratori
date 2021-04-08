package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

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
