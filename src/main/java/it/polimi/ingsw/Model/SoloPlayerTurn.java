package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.card.LeaderCard;

/*
 * SOFI*/

public class SoloPlayerTurn implements PlayerTurnInterface {
    private Player currentPlayer;
    private BoardManager boardManager;

    public SoloPlayerTurn(Player currentPlayer, BoardManager boardManager) {
        this.currentPlayer = currentPlayer;
        this.boardManager = boardManager;
    }

    @Override
    public BoardManager getBoardManager() {
        return this.boardManager;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    @Override
    public void choosePlay(TurnAction action) throws InvalidActionException {
        //case to end the turn, and then invocate the getActionToken turnaction, only in the solo game

        switch (action){
            case BUY_CARD:
                this.currentPlayer.buyCard(1,1, this.boardManager, 1);
            case BUY_FROM_MARKET:
                //this.currentPlayer.buyFromMarket();
            case ACTIVE_PRODUCTION_POWER:
                //this.currentPlayer.invokesProductionPower();
            case GET_ACTION_TOKEN:
                //the player take the action token at the end of the turn
                //get action token random
                //this.currentPlayer.getGameSpace().getActionToken
            default:
                this.currentPlayer.endTurn();
        }
    }

    @Override
    public void activeLeaderCard(int wich) throws InvalidActionException {
        LeaderCard card =  this.currentPlayer.chooseLeaderCardToActive(wich);
        if (card.getSpecialAbility().equals("Addictional Power")){
            this.currentPlayer.activeLeaderCardAbility((card), new Resource(Color.YELLOW));
        }
        else{
            this.currentPlayer.activeLeaderCardAbility(card);
        }
    }

    @Override
    public boolean checkEndGame() {
        return false;
    }

    @Override
    public boolean checkEndTurn(){
        if (!this.currentPlayer.isPlaying())
            return true;
        else
            return false;
    }
}
