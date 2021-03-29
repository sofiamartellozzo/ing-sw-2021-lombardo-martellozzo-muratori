package it.polimi.ingsw.Model.card;

public class DevelopmentCardTable {

    private DevelopmentCardDeck[][]square;

    //constructor of the class
    public DevelopmentCardTable(DevelopmentCardDeck[][] square) {
        this.square = square;
    }

    public DevelopmentCardDeck[][] getSquare() {
        return square;
    }

    public void setSquare(DevelopmentCardDeck[][] square) {
        this.square = square;
    }

}
