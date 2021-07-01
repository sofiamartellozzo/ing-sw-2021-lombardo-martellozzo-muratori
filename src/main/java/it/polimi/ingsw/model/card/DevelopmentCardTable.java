package it.polimi.ingsw.model.card;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;

import java.io.Serializable;

import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.board.CardSpace;

/**
 * table of cards composed by 12 LeaderCard Deck
 */

public class DevelopmentCardTable implements Serializable {

    private DevelopmentCardDeck[][] table;     /* Matrix made of ArrayLists of DevelopmentCard */

    /**
     * constructor of the class
     *
     * @param table
     */

    public DevelopmentCardTable(DevelopmentCardDeck[][] table) {
        this.table = table;
    }


    // Getter methods

    public DevelopmentCardDeck[][] getTable() {
        return table;
    }

    //Setter methods

    public void setSquare(DevelopmentCardDeck[][] table) {
        this.table = table;
    }

    /**
     * method used to let the player take the last card available in a specific deck ( identified by row and column )
     * and launches an exception if the deck is empty and so he can't take any card!
     *
     * @param rowPosition
     * @param columnPosition
     * @throws IllegalArgumentException
     */

    public DevelopmentCard takeCard(int rowPosition, int columnPosition) throws IllegalArgumentException {
        if ((rowPosition < 0 || rowPosition > 2) || (columnPosition < 0 || columnPosition > 3)) {
            throw new IllegalArgumentException("Error, parameters not valid!");
        } else if (table[rowPosition][columnPosition].getDevelopDeck().size() == 0) {
            throw new IllegalArgumentException("Error, you don't have other cards in this deck");
        } else return this.table[rowPosition][columnPosition].takeCard();
    }

    public void removeCard(int row, int column) {
        table[row][column].removeCard();
    }

    /**
     * method used in the solo player to remove the last card available in a specific deck
     * identified by the color, if the three decks of the color indicated are empty,
     * the method launches an exception
     *
     * @param color
     * @throws InvalidActionException
     */

    public void getSquare(Color color) throws InvalidActionException {
        boolean found = false;
        int column = selectColumn(color);

        for (int i = 2; i >= 0; i--) {
            if (!table[i][column].getDevelopDeck().isEmpty()) {
                table[i][column].takeCard();
                found = true;
                i = -1;
            }
        }
        if (!found)
            throw new InvalidActionException("Error, you don't have other cards of that color");
    }

    public void removeCardByColor(Color color) throws InvalidActionException {
        boolean found = false;
        int column = selectColumn(color);

        for (int row = 2; (row >= 0) && (!found); row--) {
            if (!table[row][column].getDevelopDeck().isEmpty()) {
                table[row][column].removeCard();
                found = true;
                row = -1;
            }
        }
        if (!found)
            throw new InvalidActionException("Error, you don't have other cards of that color");

    }

    /**
     * local method to select the column in the card Table based on the color
     *
     * @param color
     * @return
     */
    private int selectColumn(Color color) {
        switch (color) {
            case GREEN:
                return 0;
            case BLUE:
                return 1;
            case YELLOW:
                return 2;
            case PURPLE:
                return 3;
            default:
                throw new IllegalArgumentException("Error, column not valid !");
        }
    }

    /**
     * method used in the soloGame to control if a column of card (of the same color)
     * on the table is empty, in that case the player loose!
     *
     * @return
     */

    public boolean checkIfEmpty() throws InvalidActionException {

        for (int j = 0; j < 4; j++)     //columns
        {
            int count = 0;
            for (int i = 2; i >= 0; i--) {      //rows
                if (table[i][j].getDevelopDeck().isEmpty()) {
                    count += 1;
                    if (count == 3) {
                        return true;
                    }    /* if count is 3 it means that you have found 3 deck empty in the same column */
                } else {
                    i = -1;
                }    /* if the deck is not empty you can go directly to the next column */
            }
        }
        return false;
    }

    /**
     * Scans all the decks upper card of the table and check if is not empty,
     * if the player has enough resources to buy it and a card space which can contain the new card.
     * If yes, set in the matrix[row][column] true, else false.
     *
     * @return
     */
    public boolean[][] getAvailable(PlayerInterface player) {
        boolean[][] availableDecks = new boolean[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (!table[i][j].getDevelopDeck().isEmpty() && player.getGameSpace().getResourceManager().checkEnoughResources(table[i][j].getUpperCard().getCost())) {
                    for (CardSpace cardSpace : player.getGameSpace().getCardSpaces()) {
                        if ((cardSpace.getCards().size() == 0 && table[i][j].getUpperCard().getLevel() == 1) || (cardSpace.getCards().size() != 0 && cardSpace.getUpperCard().getLevel() == table[i][j].getUpperCard().getLevel() - 1)) {
                            availableDecks[i][j] = true;
                        }
                    }
                }
            }
        }
        return availableDecks;
    }

    /**
     * method used to show BUT NOT REMOVE the last card of a deck in the development table
     *
     * @param row
     * @param column
     * @return
     */
    public DevelopmentCard showLastCard(int row, int column) {
        return table[row][column].getDevelopDeck().get(table[row][column].getDevelopDeck().size() - 1);
    }


}
