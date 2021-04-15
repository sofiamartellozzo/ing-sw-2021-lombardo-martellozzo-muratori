package it.polimi.ingsw.model.card;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;

/* ILA */

/**
 SINGLETON: because I have to instance this class only once,
 and the next time
 he has to give me back the same class */

public class DevelopmentCardTable {

    private DevelopmentCardDeck[][] table;     /* Matrix made of ArrayLists of DevelopmentCard */
    private int columnPosition;
    private int rowPosition;
    private static DevelopmentCardTable developmentCardTable = null;

    /**
     * constructor of the class
     * @param table
     */

    private DevelopmentCardTable(DevelopmentCardDeck[][] table) {
        this.table = table;
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
    public DevelopmentCardDeck[][] getTable() { return table; }

    //Setter methods

    public void setSquare(DevelopmentCardDeck[][] table) { this.table = table; }
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

        return this.table[rowPosition][columnPosition].takeCard();
    }

    /**
     * method used in the solo player to remove the card of the color indicated by the actionToken
     * @param color
     * @throws InvalidActionException
     */

    public void getSquare(Color color) throws InvalidActionException {
       switch (color){
           case GREEN:
               if(table[2][0].getDevelopDeck().isEmpty())
               {
                   if (table[1][0].getDevelopDeck().isEmpty())
                   {
                       if(table[0][0].getDevelopDeck().isEmpty())
                       {
                           throw new InvalidActionException("Error, there aren't other cards of that color!");
                       }
                       else table[0][0].takeCard();
                   }
                   else table[1][0].takeCard();
               }
               else table[2][0].takeCard();
               break;
           case BLUE:
               if(table[2][1].getDevelopDeck().isEmpty())
               {
                   if (table[1][1].getDevelopDeck().isEmpty())
                   {
                       if(table[0][1].getDevelopDeck().isEmpty())
                       {
                           throw new InvalidActionException("Error, there aren't other cards of that color!");
                       }
                       else table[0][1].takeCard();
                   }
                   else table[1][1].takeCard();
               }
               else table[2][1].takeCard();
               break;
           case YELLOW:
               if(table[2][2].getDevelopDeck().isEmpty())
               {
                   if (table[1][2].getDevelopDeck().isEmpty())
                   {
                       if(table[0][2].getDevelopDeck().isEmpty())
                       {
                           throw new InvalidActionException("Error, there aren't other cards of that color!");
                       }
                       else table[0][2].takeCard();
                   }
                   else table[1][2].takeCard();
               }
               else table[2][2].takeCard();
               break;
           case PURPLE:
               if(table[2][3].getDevelopDeck().isEmpty())
               {
                   if (table[1][3].getDevelopDeck().isEmpty())
                   {
                       if(table[0][3].getDevelopDeck().isEmpty())
                       {
                           throw new InvalidActionException("Error, there aren't other cards of that color!");
                       }
                       else table[0][3].takeCard();
                   }
                   else table[1][3].takeCard();
               }
               else table[2][3].takeCard();
               break;
       }
    }

    /**
     * method used in the soloGame to control if a column of the table is empty, in that case the player loose!
     * @return
     */

    public boolean checkIfEmpty() throws InvalidActionException {

        for (int j = 0; j < 4; j++)     //columns
        {
           int count = 0;
           for (int i = 2; i >= 0 ; i--) {      //rows
               if (table[i][j].getDevelopDeck().isEmpty()) {
                   count += 1;
                   if(count == 3) {return true;}    /* if count is 3 it means that you have found 3 deck empty in the same column */
               }
               else { i = -1; }    /* if the deck is not empty you can go directly to the next column */
           }
        }
        return false;
    }


}
