package it.polimi.ingsw.Model.card;

public abstract class Card {

    protected int victoryPoints;

    public abstract int getVictoryPoints();
    public abstract void setVictoryPoints(int victoryPoints);

    // constructor of the class
    public Card(int victoryPoints){
        this.victoryPoints=victoryPoints;
    }
}
