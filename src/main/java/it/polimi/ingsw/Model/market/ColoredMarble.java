package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

/*
* SOFIA*/
public class ColoredMarble extends Marble {

    /* constructor of the class */
    public ColoredMarble(Color color) {
        super(color);
    }

    @Override
    public void choose(Player player) throws IllegalArgumentException{
        if (this.getColor() != Color.WHITE){
            Resource resource = new Resource(this.getColor());
            player.putResources(resource);
        }
        else if (this.getColor() == Color.WHITE){
            if(player.hasWhiteSpecialAbility()){
                //call the special ability of the special Marble
            }
        }
    }
}
