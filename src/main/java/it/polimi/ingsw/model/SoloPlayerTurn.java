package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.card.LeaderCard;

/**
 * this class represents the turn of a single player
 * (so he himself is always the current player of the turn, against Lorenzo)
 */
public class SoloPlayerTurn extends PlayerTurn {
    private SoloPlayer currentPlayer;

    /**
     * constructor of the class
     * @param currentPlayer --> single player
     * @param boardManager
     */
    public SoloPlayerTurn(SoloPlayer currentPlayer, BoardManager boardManager) {
        super(currentPlayer, boardManager);
        this.currentPlayer = currentPlayer;
    }

    @Override
    public SoloPlayer getCurrentPlayer() {
        return currentPlayer;
    }


    @Override
    public void activeLeaderCard(int which) throws InvalidActionException {
        LeaderCard card =  this.currentPlayer.selectLeaderCard(which);
        if (card.getSpecialAbility().equals("Addictional Power")){
            this.currentPlayer.activeLeaderCardAbility((card), new Resource(Color.YELLOW));
        }
        else{
            this.currentPlayer.activeLeaderCardAbility(card);
        }
    }

    @Override
    public int discardLeaderCard(int which) throws InvalidActionException {
        LeaderCard card = this.currentPlayer.selectLeaderCard(which);
        if (card!=null){
            return currentPlayer.removeLeaderCard(card);
        }
        return 0;

    }

    @Override
    public boolean checkEndTurn(){
        if (!this.currentPlayer.isPlaying())
            return true;
        else
            return false;
    }

    /**
     * method called at the end of each Solo Turn
     * this method activates an action token that represents the turn of lorenzo il magnifico
     */
    public ActionToken activateActionToken() throws InvalidActionException {
        ActionToken actionTokenActivated = this.currentPlayer.getGameSpace().playActionToken(getBoardManager(), this.currentPlayer);
        return actionTokenActivated;
    }
}
