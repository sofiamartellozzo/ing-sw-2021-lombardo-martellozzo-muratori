package it.polimi.ingsw.Model;

/*
SOFI
put this class in the manager, so he ask the right one to do the right buyCard
 */

public interface BuyCard {
    public void buyCard(int x, int y, PlayerTurnInterface actualTurn, int selectedCardSpace);
}
