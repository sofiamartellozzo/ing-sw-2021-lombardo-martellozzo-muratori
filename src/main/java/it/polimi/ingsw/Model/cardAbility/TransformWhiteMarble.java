package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Model.Resource;


/*
* GIANLUCA
* */
// It allows to the Player to take one Resource (indicated by the Special Ability)
// for each White Marble he draws

public class TransformWhiteMarble extends SpecialAbility{
    //Constructor
    public TransformWhiteMarble(boolean active, Resource type) {
        super(active, type);
    }

    @Override
    public void activeAbility() {
        super.activeAbility();
    }
}
