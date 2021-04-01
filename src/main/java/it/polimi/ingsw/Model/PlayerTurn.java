package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.cardAbility.Discount;
import it.polimi.ingsw.Model.cardAbility.SpecialAbility;

/*
 * SOFI*/

public class PlayerTurn implements PlayerTurnInterface {
    private Player currentPlayer;
    private BoardManager boardManager;

    public PlayerTurn(Player currentPlayer, BoardManager boardManager) {
        this.currentPlayer = currentPlayer;
        this.boardManager = boardManager;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void chosePlay(TurnAction action){
        switch (action){
            case BUY_CARD:
                //I need the input from the real player (person)
                this.currentPlayer.buyCard(1,1, this, 1);
            case ACTIVE_CARD:
                //method to take wich card the player want, that return the relative int
                int num = 1; //just for not having error, rapresent the card that the player choose
                LeaderCard card =  this.currentPlayer.chooseLeaderCardToActive(num);
                this.currentPlayer.activeLeaderCardAbility(card);
            case BUY_FROM_MARKET:
                this.currentPlayer.buyFromMarket();
            case ACTIVE_PRODUCTION_POWER:
                //this.currentPlayer.invokesProductionPower();
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
