package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Model.Resource;


/*
* GIANLUCA
* */
// It creates a Special Depot in which the Player can insert
// maximum two Resources (indicated by the "type" attribute
// of the SpecialAbility abstract class

public class SpecialDepot extends SpecialAbility{
    // Constructor
    public SpecialDepot(boolean active, Resource type) {
        super(active, type);
    }

    @Override
    public void activeAbility() {
        super.activeAbility();
        //New BuyDiscount
    }
}
