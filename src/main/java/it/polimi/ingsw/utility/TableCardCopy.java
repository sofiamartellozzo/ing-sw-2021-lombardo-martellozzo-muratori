package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.card.DevelopmentCardDeck;

import java.io.Serializable;

/**
 * this class represents a model Table Card , filled with Development Card Decks
 * (a copy of th model Class useful to send to the net)
 */
public class TableCardCopy implements Serializable {

    private DevelopmentCardDeck[][] table;

    public TableCardCopy(){ table = new DevelopmentCardDeck[3][4]; }

    /**
     * method used to fill (and create) the copy of the card table to use in CLI
     * @param cardTable
     * @return
     */
    public void storeTable(DevelopmentCardDeck[][] cardTable){
        table = cardTable;
    }

    /**
     * this method returns the deck in a specific position in the Development card table
     * @param row
     * @param column
     * @return
     */
    public DevelopmentCardDeck getDeckTable(int row, int column){
        return table[row][column];
    }
}
