package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.card.LeaderCard;

import java.util.ArrayList;

/*
 * SOFI*/

public class PlayerTurn implements PlayerTurnInterface {
    private Player currentPlayer;
    private BoardManager boardManager;
    private ArrayList<TurnAction> availableAction;

    public PlayerTurn(Player currentPlayer, BoardManager boardManager) {
        this.currentPlayer = currentPlayer;
        this.boardManager = boardManager;
        availableAction = new ArrayList<>();
        setAvailableAction();
    }

    private void setAvailableAction(){
        for (TurnAction action: TurnAction.values()) {
            availableAction.add(action);
        }
        //at first he cannot pass, he have to choose at least one action, than can pass ending the turn
        availableAction.remove(TurnAction.END_TURN);
        availableAction.remove(TurnAction.GET_ACTION_TOKEN);
        availableAction.remove(TurnAction.ERROR);
    }


    @Override
    public ArrayList<TurnAction> getAvailableAction() {
        return availableAction;
    }

    /**
     * method called after an action of a player that can't be activated again
     * @param actionToRemove
     */
    @Override
    public void removeAction(TurnAction actionToRemove){
        ArrayList<TurnAction> onlyOneOfThese = new ArrayList<>();
        onlyOneOfThese.add(TurnAction.BUY_CARD);
        onlyOneOfThese.add(TurnAction.BUY_FROM_MARKET);
        onlyOneOfThese.add(TurnAction.ACTIVE_PRODUCTION_POWER);
        if (onlyOneOfThese.contains(actionToRemove)){
            //the player did one of these 3, so I have to eliminate all of them from the available ones in this class
            availableAction.removeAll(onlyOneOfThese);
        }
        else if (actionToRemove.equals(TurnAction.SEE_OTHER_PLAYER)){
            availableAction.remove(actionToRemove);
        }
    }

    /**
     * method called to add an action
     * @param actionToAdd
     */
    @Override
    public void addAction(TurnAction actionToAdd) {
        availableAction.add(actionToAdd);
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    @Override
    public void activeLeaderCard(int which) throws InvalidActionException {
        LeaderCard card =  this.currentPlayer.selectLeaderCard(which);
        if(card!=null){
            this.currentPlayer.activeLeaderCardAbility(card);
        }else{
            throw new InvalidActionException("Card not valid!");
        }

    }

    @Override
    public void discardLeaderCard(int which) throws InvalidActionException {
        LeaderCard card =  this.currentPlayer.selectLeaderCard(which);
        if (card!=null){
            currentPlayer.removeLeaderCard(card);

        }
    }


    public boolean checkEndGame() throws InvalidActionException{
        return false;
    }

    /* check if the player ended the turn or not */
    public boolean checkEndTurn(){
        if (!this.currentPlayer.isPlaying())
            return true;
        else
            return false;
    }
}
