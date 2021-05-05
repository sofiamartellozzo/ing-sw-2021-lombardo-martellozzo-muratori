package it.polimi.ingsw.model.card;

public abstract class Card {

    /* ILA */

    protected int victoryPoints;

    /**
     * constructor
     * @param victoryPoints
     */

    public Card(int victoryPoints){
        this.victoryPoints=victoryPoints;
    }
    public abstract int getVictoryPoints();}


