package it.polimi.ingsw.Model.board;

/*
* GIANLUCA
* One of the type of the boxes.
* It has the same attribute of Box class.
* Additional attribute:
* victoryPoints -> The value of Victory Points, to calculate the final score.
* */

public class GoldBox extends Box{
    private final int victoryPoints;

    /*
    * Constructor
    * */
    public GoldBox(int whichSection, int numberBox, int victoryPoints) {
        super(whichSection,numberBox);
        this.victoryPoints = victoryPoints;
    }

    /*
    * Getter Method
    * */
    public int getVictoryPoints() {
        return victoryPoints;
    }
}
