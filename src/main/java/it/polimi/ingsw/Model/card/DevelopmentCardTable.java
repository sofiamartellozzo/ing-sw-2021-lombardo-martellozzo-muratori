package it.polimi.ingsw.Model.card;

import java.util.ArrayList;

/* ILA */

public class DevelopmentCardTable {

    private DevelopmentCardDeck[][] square;     /* Matrix made of ArrayLists of DevelopmentCard */
    private int columnPosition;
    private int rowPosition;

    //constructor of the class
    public DevelopmentCardTable(DevelopmentCardDeck[][] square) {
        this.square = square;
    }

    // Getter methods

    public int getColumnPosition() { return columnPosition; }
    public int getRowPosition() { return rowPosition; }
    public DevelopmentCardDeck[][] getSquare() { return square; }

    //Setter methods

    public void setSquare(DevelopmentCardDeck[][] square) { this.square = square; }
    public void setRowPosition(int rowPosition){this.rowPosition=rowPosition;}
    public void setColumnPosition(int columnPosition) { this.columnPosition = columnPosition; }


    //method used to show the last card of the deck that the player chose
    public void takeCard(int rowPosition, int columnPosition)
    {

    }


}
