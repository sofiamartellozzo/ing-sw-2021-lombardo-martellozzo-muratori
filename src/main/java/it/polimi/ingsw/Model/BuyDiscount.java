package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exception.InvalidActionException;
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
    public void buyCard(int row, int column, BoardManager boardManager, Player player, int selectedCardSpace) throws InvalidActionException {
        //same as buy card but with discount
        /* take the payment for the card, applicate the discount, then remove it from the table*/
        DevelopmentCard cardBought = boardManager.getDevelopmentCardTable().takeCard(row,column);
        ArrayList<Resource> cost = cardBought.getCost();
        PersonalBoard playerBoard = player.getGameSpace();
        /*apply the discount*/
        cost.removeAll(resourceWithDiscount);
        if (checkBeforeBuy(cardBought, player)){
            playerBoard.getResourceManager().removeResources(cost);
            //playerBoard.removeResource(cost.get(1), new RealDepot(1,1));
            playerBoard.getCardSpaces().get(selectedCardSpace).addCard(cardBought);
        }
        else{
            throw new InvalidActionException("Not enought resources for buy this Development Card!");
        }

    }

    private boolean checkBeforeBuy(DevelopmentCard card, Player player){
        ArrayList<Resource> requirements = new ArrayList<>();
        ArrayList<Resource> resourcesOwned = new ArrayList<>();
        requirements = card.getCost();
        resourcesOwned = player.getGameSpace().getResourceManager().getResources();
        if (resourcesOwned.containsAll(requirements))
            return true;
        else
            return false;

    }

    @Override
    public String toString(){
        return "BuyDiscount";
    }

}
