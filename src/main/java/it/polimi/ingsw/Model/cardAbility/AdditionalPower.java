package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.card.SpecialCard;

import java.util.ArrayList;

/**
 * GIANLUCA
 * TEST PASSED
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
     * It sets the Ability as active (super.activeAbility) and
     * creates a Special Card object with an ArrayList of resources (with a single resource into)
     * in input. After the creation, adds the Special Card in the player specialCard attribute,
     * so the new Production Power is available to the player whom can use it.
     */
    @Override
    public void activeAbility(Player player) throws InvalidActionException {
        super.activeAbility(player);
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(this.getResource());
        SpecialCard specialCard = new SpecialCard(resources);
        player.addSpecialCard(specialCard);
    }

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "AdditionalPower";
    }
}
