package it.polimi.ingsw.Model.actionAbility;

import it.polimi.ingsw.Model.Color;

/*
 * SOFI*/

public class CardActionAbility implements ActionAbility {
    private Color color;

    public CardActionAbility(Color color) {
        this.color = color;
    }

    public void activeAbility(){
        //remove two card of this.color
    }
}
