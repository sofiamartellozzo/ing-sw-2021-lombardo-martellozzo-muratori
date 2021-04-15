package it.polimi.ingsw.model;

/*
SOFI
put this class in the manager, so he ask the right one to do the right buyCard
 */

import it.polimi.ingsw.exception.InvalidActionException;

public interface BuyCard {
    public void buyCard(int x, int y, BoardManager boardManager, Player player, int selectedCardSpace) throws InvalidActionException;
}
