package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;

public abstract class Marble {
    private Color color;
    private int[][] position;

    //Constructor
    public Marble(Color color, int[][] position) {
        this.color = color;
        this.position = position;
    }

    //Mette la risorsa al posto giusto o fa quello che deve
    public void choose(Player player){};
}
