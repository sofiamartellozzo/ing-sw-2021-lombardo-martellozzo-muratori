package it.polimi.ingsw.model.card;

import java.io.Serializable;

public abstract class Card implements Serializable {

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


