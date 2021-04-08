package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.card.DevelopmentCard;
import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.cardAbility.Discount;
import it.polimi.ingsw.Model.cardAbility.SpecialAbility;

import java.util.ArrayList;

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

    public void choosePlay(TurnAction action) throws InvalidActionException{
        /*metti che può scartare la carta leader*/
        switch (action){
            case BUY_CARD:
                //I need the input from the real player (person)
                this.currentPlayer.buyCard(1,1, this.boardManager, 1);
            case MOVE_RESOURCE:
                this.currentPlayer.moveResource(1,3);
            case BUY_FROM_MARKET:
                this.currentPlayer.buyFromMarket(1, "row", this.boardManager);
            case ACTIVE_PRODUCTION_POWER:
                ArrayList<DevelopmentCard> ppCard = new ArrayList<>();
                ArrayList<Resource> cost = new ArrayList<>();
                cost.add(new Resource(Color.YELLOW));
                ppCard.add(new DevelopmentCard(1,Color.GREEN, 2,cost,cost,cost));
                this.currentPlayer.invokesProductionPower(ppCard);
            case REMOVE_LEADER_CARD:
                this.currentPlayer.removeLeaderCard(1);
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


    public boolean checkEndGame(){
        return true;
    }

    /* check if the player ended the turn or not */
    public boolean checkEndTurn(){
        if (!this.currentPlayer.isPlaying())
            return true;
        else
            return false;
    }
}
