package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.card.SpecialCard;

/**
 * GIANLUCA
 * It refers to the ability of the Production
 * Power provided by the LeaderCard
 * */

public class AdditionalPower extends SpecialAbility{
    /**
     * Constructor
     * @param resource
     */
    public AdditionalPower(Resource resource) {
        super(resource);
    }

    /**
     * It sets the Ability as active and creates a Special Card object
     */
    @Override
    public void activeAbility(Player player) throws InvalidActionException {
        super.activeAbility(player);
        SpecialCard specialCard = new SpecialCard(this.getResource());
        player.getSpecialCard().addSpecialCard(specialCard);
    }
}
