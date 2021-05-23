package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.actionAbility.ActionAbility;
import it.polimi.ingsw.model.actionAbility.CardActionAbility;
import it.polimi.ingsw.model.actionAbility.PlusTwoBlackCrossActionAbility;
import it.polimi.ingsw.model.actionAbility.PlusUneAndShuffleActionAbility;

import java.io.Serializable;

/*
 * SOFI*/

/**
 * class of cards used in individual mode game
 * them are stored in the Solo Personal Bord
 * (=the board specific for the single player)
 */

public class ActionToken implements Serializable {

    /* the card have an ability that is activated when draw this card */
    private ActionAbility ability;

    private int cardID;

    /* constructor */
    public ActionToken(ActionAbility ability, int cardID) {
        this.ability = ability;
        this.cardID = cardID;
    }

    /**
     * method called when the card is draw, so his ability is activated
     * @param boardManager, for have access to the game space
     * @param player that has draw the card
     * @throws InvalidActionException
     */
    public void activeActionToken(BoardManager boardManager, SoloPlayer player) throws InvalidActionException {
        this.ability.activeAbility(boardManager, player);
    }

    public int getCardID() {
        return cardID;
    }

    public ActionAbility getActionAbility(){
        return ability;
    }

    public String getAbility() {
       if(ability instanceof CardActionAbility){
           return "Card Action Ability";
       }
       else if(ability instanceof PlusTwoBlackCrossActionAbility){
           return "Plus Two Black Cross Action Ability";
       }
       else if(ability instanceof PlusUneAndShuffleActionAbility){
           return "Plus One And Shuffle Action Ability";
       }
       else{
           throw new IllegalArgumentException(" error, this action token not exist");
       }
    }

    @Override
    public String toString() {
        return "ActionToken{" +
                "ability=" + ability +
                "id: " +cardID+
                '}';
    }
}
