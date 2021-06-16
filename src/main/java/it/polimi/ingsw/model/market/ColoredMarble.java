package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.io.Serializable;

/**
 * this class represents a normal Marble present in the Market
 * it is represented by a color, that color represents the Resource that will be given to the player
 * when he buys from the Market
 * each color refers to a specific resource:
 * RED --> FAITH MARKER
 * BLUE --> SHIELD
 * GREY --> STONE
 * YELLOW --> COIN
 * PURPLE --> SERVANT
 */
public class ColoredMarble extends Marble implements Serializable {

    /**
     * constructor of the class
     * @param color --> color of the marble
     */
    public ColoredMarble(Color color){
            this.color = color;
    }

    @Override
    public TypeResource choose(Player player) throws InvalidActionException {
        if (this.getColor() != Color.WHITE){
            Resource resource = new Resource(this.getColor());
            return resource.getType();
            //player.putResources(resource);
        }
        else if (this.getColor() == Color.WHITE){

            if(player.getWhiteSpecialMarble() != null){

                //call the special ability of the special Marble
                return player.getWhiteSpecialMarble().choose(player);
            }
        }
        return null;
    }
}
