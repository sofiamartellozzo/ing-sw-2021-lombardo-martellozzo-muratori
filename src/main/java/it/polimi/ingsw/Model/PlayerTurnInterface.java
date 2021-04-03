package it.polimi.ingsw.Model;

/* in reality I could do this abstract and implement the same method
* just the emd game is different so that method will be not in this class but in the single one
* that extends this*/

import it.polimi.ingsw.Exception.InvalidActionException;

public interface PlayerTurnInterface {

    public BoardManager getBoardManager();

    public Player getCurrentPlayer();

    public void choosePlay(TurnAction action) throws InvalidActionException;

    public void activeLeaderCard(int wich) throws InvalidActionException;

    public boolean checkEndGame();

    public boolean checkEndTurn();
}
