package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.card.LeaderCard;

import java.util.ArrayList;

/*
 * SOFI*/

public class SoloPlayerTurn extends PlayerTurn {
    private SoloPlayer currentPlayer;

    public SoloPlayerTurn(SoloPlayer currentPlayer, BoardManager boardManager) {
        super(currentPlayer, boardManager);
        this.currentPlayer = currentPlayer;
        addAction(TurnAction.GET_ACTION_TOKEN);
    }

    @Override
    public SoloPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void choosePlay(TurnAction action) throws InvalidActionException {
        //case to end the turn, and then invocate the getActionToken turnaction, only in the solo game

        switch (action){
            case BUY_CARD:
                this.currentPlayer.buyCard(1,1, this.getBoardManager(), 1);
            case BUY_FROM_MARKET:
                //this.currentPlayer.buyFromMarket();
            case ACTIVE_PRODUCTION_POWER:
                //this.currentPlayer.invokesProductionPower();
            case GET_ACTION_TOKEN:
                //the player take the action token at the end of the turn
                //get action token random
                this.currentPlayer.getGameSpace().getActionTokenDeck(this.getBoardManager(), this.currentPlayer);
            default:
                this.currentPlayer.endTurn();
        }
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
    public void discardLeaderCard(int which) {

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
        ActionToken actionTokenActivated = this.currentPlayer.getGameSpace().getActionTokenDeck(this.getBoardManager(), this.currentPlayer);
        return actionTokenActivated;
    }
}
