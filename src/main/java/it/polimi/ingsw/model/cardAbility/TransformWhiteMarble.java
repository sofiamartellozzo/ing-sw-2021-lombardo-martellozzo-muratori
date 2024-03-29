package it.polimi.ingsw.model.cardAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.market.Ability;
import it.polimi.ingsw.model.market.MarbleSpecial;

import java.io.Serializable;


/**
 * It allows the Player to take one Resource (indicated by the Special Ability)
 * for each White Marble he draws from the market
 * If are activated two abilities of TransformWhiteMarble the player, foreach white marble taken from the market,
 * will have to choose from the two types specified
* */

public class TransformWhiteMarble extends SpecialAbility implements Serializable {
    /**
     * Constructor
     * @param resource
     */
    public TransformWhiteMarble(Resource resource) {
        super(resource, TypeAbility.TRANSFORM_WHITE);
    }

    /**
     * Creates a new Ability object containing the resource indicates by the Leader Card Special Ability,
     * then gets the MarbleSpecial object from the player.
     * If not exists, creates one, inserts the Ability object in and sets the Special Marble in Player.
     * Else, just inserts the Ability object in the existing one.
     * @param player
     * @throws InvalidActionException
     */
    @Override
    public void activeAbility(Player player) throws InvalidActionException {

        super.activeAbility(player);
        Ability ability = new Ability(getResource());
        MarbleSpecial marbleSpecial = player.getWhiteSpecialMarble();
        if(marbleSpecial==null) {
            marbleSpecial = new MarbleSpecial();
            marbleSpecial.setAbility(ability);
            player.setWhiteSpecialMarble(marbleSpecial);
        }else{
            marbleSpecial.setAbility(ability);
        }

    }

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return "TransformWhiteMarble";
    }
}
