package it.polimi.ingsw.Model.card;

import java.util.ArrayList;

/* ILA */

/* SINGLETON: because I have to instance this class only once,
and the next time
he has to give me back the same class*/

public class DevelopmentCardTable {

    private DevelopmentCardDeck[][] square;     /* Matrix made of ArrayLists of DevelopmentCard */
    private int columnPosition;
    private int rowPosition;
    private static DevelopmentCardTable developmentCardTable = null;

    /**
     * constructor of the class
     * @param square
     */

    private DevelopmentCardTable(DevelopmentCardDeck[][] square) {
        this.square = square;
    }

    public static DevelopmentCardTable getInstance(DevelopmentCardDeck[][] square)
    {
        if (developmentCardTable == null)
        {
            developmentCardTable = new DevelopmentCardTable(square);
        }
        return developmentCardTable;
    }

    // Getter methods

    public int getColumnPosition() { return columnPosition; }
    public int getRowPosition() { return rowPosition; }
    public DevelopmentCardDeck[][] getSquare() { return square; }

    //Setter methods

    public void setSquare(DevelopmentCardDeck[][] square) { this.square = square; }
    public void setRowPosition(int rowPosition){this.rowPosition=rowPosition;}
    public void setColumnPosition(int columnPosition) { this.columnPosition = columnPosition; }

    /**
     * method used to show the last card of the deck that the player chose in the Matrix
     * @param rowPosition
     * @param columnPosition
     * @throws IllegalArgumentException
     */

    public DevelopmentCard takeCard (int rowPosition, int columnPosition) throws IllegalArgumentException
    {
        if ((rowPosition < 1 || rowPosition > 3) || (columnPosition < 1 || columnPosition > 4))
        {
            throw new IllegalArgumentException("Error, parameters not valid!");
        }

        return this.square[rowPosition][columnPosition].takeCard();
    }
}
