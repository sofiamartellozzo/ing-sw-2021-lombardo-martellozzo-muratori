package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.card.LeaderCard;

/*
 * SOFI*/

public class SoloPlayerTurn extends PlayerTurn {
    private SoloPlayer currentPlayer;

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
    public void discardLeaderCard(int which) throws InvalidActionException {
        LeaderCard card = this.currentPlayer.selectLeaderCard(which);
        if (card!=null){
            currentPlayer.removeLeaderCard(card);
        }

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
     */
    public ActionToken activateActionToken() throws InvalidActionException {
        ActionToken actionTokenActivated = this.currentPlayer.getGameSpace().playActionToken(this.getBoardManager(), this.currentPlayer);
        return actionTokenActivated;
    }
}
