package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.board.resourceManagement.RealDepot;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;
import it.polimi.ingsw.Model.card.DevelopmentCard;

import java.util.HashMap;

/*
* SOFI*/

public class Buy implements BuyCard {
    @Override
    public void buyCard(int row, int column, PlayerTurnInterface currentTurn, int selectedCardSpace) {
        //when the player decides to puy a card
        /* took the card payment.. then remove the card from the table ecc..*/
        DevelopmentCard cardBought = currentTurn.getBoardManager().getDevelopmentCardTable().takeCard(row,column);
        HashMap<Integer, Resource> cost = cardBought.getCost();
        PersonalBoard playerBoard = currentTurn.getCurrentPlayer().getGameSpace();
        //create a private method for the payment
        playerBoard.getWarehouse().removeResource(cost.get(1), new RealDepot(1,1));
        playerBoard.getCardSpaces().get(selectedCardSpace).addCard(cardBought);
    }

    private void payment(HashMap<Integer, Resource> cost, WarehouseStandard fromHere){
        //here I pay for the card, from the wharehouse

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
