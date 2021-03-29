package it.polimi.ingsw.Model.cardability;

import it.polimi.ingsw.Model.Resource;

// It refers to the ability of the Discount when the relative Player
// buy a Development Card

public class Discount extends SpecialAbility{
    //Constructor
    public Discount(boolean active, Resource type) {
        super(active, type);
    }

    @Override
    public void activeAbility() {
        super.activeAbility();
    }
}
