package it.polimi.ingsw.Model.card;

import it.polimi.ingsw.Exception.InvalidActionException;

import java.util.ArrayList;

/* ILA */

public class DevelopmentCardDeck {

    private ArrayList<DevelopmentCard> developDeck;

    //constructor of the class
    public DevelopmentCardDeck(ArrayList<DevelopmentCard> developDeck) {
        this.developDeck = developDeck;
    }

    public ArrayList<DevelopmentCard> getDevelopDeck() {
        return developDeck;
    }

    public void setDevelopDeck(ArrayList<DevelopmentCard> developDeck) {
        this.developDeck = developDeck;
    }

    //method used to show the last card avaiable in a deck, if it is not empty
    public DevelopmentCard takeCard() throws IndexOutOfBoundsException {
        if (developDeck.size() == 0)
            throw new IndexOutOfBoundsException("Error, the deck is empty,so you can't take a card!!");
        else return developDeck.get(developDeck.size()-1);
    }
}
