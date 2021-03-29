package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.card.LeaderCard;

public class PlayerTurn implements PlayerTurnInterface {
    private Player currentPlayer;

    public PlayerTurn(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void chosePlay(TurnAction action){
        switch (action){
            case BUY_CARD:
                this.currentPlayer.buyCard();
            case ACTIVE_CARD:
                //method to take wich card the player want, that return the relative int
                int num = 1; //just for not having error
                LeaderCard card =  this.currentPlayer.chooseLeaderCardToActive(num);
                this.currentPlayer.activeLeaderCardAbility(card);
            case BUY_FROM_MARKET:
                this.currentPlayer.buyFromMarket();
            case ACTIVE_PRODUCTION_POWER:
                this.currentPlayer.invokesProductionPower();
        }
    }

    public boolean checkEndGame(){
        return true;
    }

    /* check if the player ended the turn or not */
    public boolean checkEndTurn(){
        if (this.currentPlayer.endTurn())
            return true;
        else
            return false;
    }
}
