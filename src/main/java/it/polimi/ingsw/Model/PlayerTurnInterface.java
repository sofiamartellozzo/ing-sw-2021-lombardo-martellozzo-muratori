package it.polimi.ingsw.Model;

/* in reality I could do this abstract and implement the same method
* just the emd game is different so that method will be not in this class but in the single one
* that extends this*/

public interface PlayerTurnInterface {

    public abstract Player getCurrentPlayer();

    public abstract void chosePlay(TurnAction action);

    public abstract boolean checkEndGame();

    public abstract boolean checkEndTurn();
}
