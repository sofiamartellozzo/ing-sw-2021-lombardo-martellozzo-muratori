package it.polimi.ingsw.Model.card;

import java.util.ArrayList;

public class DevelopmentCardDeck {

    private int[][]position;
    private ArrayList<DevelopmentCard> composedOf;

    //constructor of the class

    public DevelopmentCardDeck(int[][]position,ArrayList<DevelopmentCard> composedOf) {
        this.composedOf = composedOf;
        this.position=position;
    }

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    public ArrayList<DevelopmentCard> getComposedOf() {
        return composedOf;
    }

    public void setComposedOf(ArrayList<DevelopmentCard> composedOf) {
        this.composedOf = composedOf;
    }

    //method used to remove the last card avaiable in a deck
    public void removeCard(DevelopmentCard card){composedOf.remove(card);}

}
