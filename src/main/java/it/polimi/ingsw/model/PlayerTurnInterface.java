package it.polimi.ingsw.model;

/* in reality I could do this abstract and implement the same method
* just the emd game is different so that method will be not in this class but in the single one
* that extends this*/

import it.polimi.ingsw.exception.InvalidActionException;

import java.util.ArrayList;

public interface PlayerTurnInterface {

    public ArrayList<TurnAction> getAvailableAction();

    public void removeAction(TurnAction actionToRemove);

    public void addAction(TurnAction actionToAdd);

    public BoardManager getBoardManager();

    public Player getCurrentPlayer();


    public void activeLeaderCard(int which) throws InvalidActionException;

    public void discardLeaderCard(int which) throws InvalidActionException;

    public boolean checkEndGame() throws InvalidActionException;

    public boolean checkEndTurn();
}
