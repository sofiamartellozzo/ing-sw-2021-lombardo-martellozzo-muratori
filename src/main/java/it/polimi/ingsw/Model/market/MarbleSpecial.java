package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;

import java.util.ArrayList;

/*
 * GIANLUCA
 * */
public class MarbleSpecial extends Marble {
    private ArrayList<Ability> ability;

    public MarbleSpecial(Color color, int[][] position) {
        super(color, position);
    }

    public void choose(){};
}
