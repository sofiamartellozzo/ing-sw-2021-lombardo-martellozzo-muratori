package it.polimi.ingsw.model.card;

import java.io.Serializable;

/**
 * super class of all the cards that are present in the game
 * DEVELOPMENT CARD, LEADER CARD, SPECIAL CARD (if a leader card is active)
 */
public abstract class Card implements Serializable {

    protected int victoryPoints;

    /**
     * constructor
     * @param victoryPoints
     */

    public Card(int victoryPoints){
        this.victoryPoints=victoryPoints;
    }
    public abstract int getVictoryPoints();}


