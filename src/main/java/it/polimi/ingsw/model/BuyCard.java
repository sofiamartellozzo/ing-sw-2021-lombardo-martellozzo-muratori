package it.polimi.ingsw.model;
import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.exception.InvalidActionException;

/**
 * generic interface used to buy a card (then implemented with and without the discount)
 * put this class in the manager, so he ask the right one to do the right buyCard
 */
public interface BuyCard {
    public void buyCard(int x, int y, BoardManager boardManager, Player player, int selectedCardSpace) throws InvalidActionException, CardSpaceException;
}
