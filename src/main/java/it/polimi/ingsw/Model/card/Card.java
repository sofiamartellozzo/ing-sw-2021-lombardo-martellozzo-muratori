package it.polimi.ingsw.Model.card;

public abstract class Card {

    protected int victoryPoints;

    /**
     * constructor
     * @param victoryPoints
     */
    public Card(int victoryPoints){
        this.victoryPoints=victoryPoints;
    }

    public abstract int getVictoryPoints();
}
