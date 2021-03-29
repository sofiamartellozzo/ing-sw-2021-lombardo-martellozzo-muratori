package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.card.LeaderCard;

public class SoloPlayerTurn implements PlayerTurnInterface {
    private Player currentPlayer;

    public SoloPlayerTurn(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    @Override
    public void chosePlay(TurnAction action) {

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
