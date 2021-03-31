package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.card.DevelopmentCard;

import java.util.ArrayList;

/*
 * SOFI*/

public class BuyDiscount implements BuyCard{

    /*
    * created by Dicount Ability */

    private ArrayList<Resource> resourceWithDiscount;

    /* constructor of the class */
    public BuyDiscount() {
        this.resourceWithDiscount = new ArrayList<>();
    }

    public ArrayList<Resource> getResourceWithDiscount() {
        return resourceWithDiscount;
    }

    public void addResourceWithDiscount(Resource resource) {
        this.resourceWithDiscount.add(resource);
    }

    @Override
    public void buyCard(int x, int y, PlayerTurnInterface currentTurn, int selectedCardSpace) {
        //same as buy card but with discount
        /* take the payment for the card, applicate the discount, then remove it from the table*/
        DevelopmentCard cardBought = currentTurn.getBoardManager().getDevelopmentCardTable().takeCard(row,column);
        PersonalBoard playerBoard = currentTurn.getCurrentPlayer().getGameSpace();
        playerBoard.getCardSpaces().get(selectedCardSpace).addCard(cardBought);
    }
}
