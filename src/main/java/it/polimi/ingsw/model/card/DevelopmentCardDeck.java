package it.polimi.ingsw.model.card;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * deck of the table composed by 4 development cards
 * each deck has a specific color and level
 */
public class DevelopmentCardDeck implements Serializable {

    private ArrayList<DevelopmentCard> developDeck;

    /**
     * constructor of the class
     * @param developDeck
     */
    public DevelopmentCardDeck(ArrayList<DevelopmentCard> developDeck) {
        this.developDeck = developDeck;
    }

    public ArrayList<DevelopmentCard> getDevelopDeck() {
        return developDeck;
    }

    public void setDevelopDeck(ArrayList<DevelopmentCard> developDeck) {
        this.developDeck = developDeck;
    }

    /**
     * method used to show the last card available in a deck,
     * if it is not empty the method returns and removes it, otherwise it launches an exception
     * @return --> Development Card
     * @throws IndexOutOfBoundsException
     */

    public DevelopmentCard takeCard() throws IndexOutOfBoundsException {
        if (developDeck.size() == 0)
            throw new IndexOutOfBoundsException("Error, the deck is empty,so you can't take a card!!");
        else return developDeck.remove(developDeck.size()-1);
    }

    public void removeCard(){
        developDeck.remove(developDeck.size()-1);
    }

    public DevelopmentCard getUpperCard(){
        return developDeck.get(developDeck.size()-1);
    }

}
