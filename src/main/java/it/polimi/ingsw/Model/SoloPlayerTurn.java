package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.card.LeaderCard;

/*
 * SOFI*/

public class SoloPlayerTurn implements PlayerTurnInterface {
    private Player currentPlayer;
    private SoloBoardManager boardManager;

    public SoloPlayerTurn(Player currentPlayer, SoloBoardManager boardManager) {
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
    public void chosePlay(TurnAction action) {
        //case to end the turn, and then invocate the getActionToken turnaction, only in the solo game

        switch (action){
            case BUY_CARD:
                this.currentPlayer.buyCard(1,1, this, 1);
            case ACTIVE_CARD:
                //method to take wich card the player want, that return the relative int
                int num = 1; //just for not having error
                LeaderCard card =  this.currentPlayer.chooseLeaderCardToActive(num);
                this.currentPlayer.activeLeaderCardAbility(card);
            case BUY_FROM_MARKET:
                this.currentPlayer.buyFromMarket();
            case ACTIVE_PRODUCTION_POWER:
                //this.currentPlayer.invokesProductionPower();
            case GET_ACTION_TOKEN:
                //the player take the action token at the end of the turn
                //get action token random
                this.boardManager.getActionTokenDeck();
            default:
                this.currentPlayer.endTurn();
        }
    }

    @Override
    public boolean checkEndGame() {
        return false;
    }

    @Override
    public boolean checkEndTurn() {
        if (this.currentPlayer.endTurn())
            return true;
        else
            return false;
    }
}
