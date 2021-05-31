package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * SOFI*/

public class BuyDiscount implements BuyCard, Serializable {

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
    public void buyCard(int row, int column, BoardManager boardManager, Player player, int selectedCardSpace) throws InvalidActionException, CardSpaceException {
        //same as buy card but with discount
        /* take the payment for the card, applicate the discount, then remove it from the table*/
        DevelopmentCard cardBought = boardManager.getDevelopmentCardTable().takeCard(row,column);
        ArrayList<Resource> cost = cardBought.getCost();
        PersonalBoard playerBoard = player.getGameSpace();
        /*apply the discount*/
        //cost.removeAll(resourceWithDiscount); --->these are not exacly the same object, I have to remove the one by one
        for (Resource rDiscounted:resourceWithDiscount){
            for (Resource resource: cost) {
                if (resource.getType().equals(rDiscounted.getType()))
                    cost.remove(resource);
            }
        }
        if (checkBeforeBuy(cardBought, player, cost)){
            playerBoard.getResourceManager().removeResourcesFromBoth(cost);
            //playerBoard.removeResource(cost.get(1), new RealDepot(1,1));
            playerBoard.getCardSpaces().get(selectedCardSpace).addCard(cardBought);
        }
        else{
            throw new InvalidActionException("Not enought resources for buy this Development Card!");
        }

    }

    private boolean checkBeforeBuy(DevelopmentCard card, Player player, ArrayList<Resource> requirements){
        ArrayList<Resource> resourcesOwned = new ArrayList<>();
        //requirements = card.getCost(); ---> I give the cost in input because I applyed the discount in the method above
        resourcesOwned = player.getGameSpace().getResourceManager().getResources();
        int i = 0;
        boolean find = false;
        for (Resource resource: requirements){
            for (int r=0; (!resourcesOwned.isEmpty())&&(0<resourcesOwned.size())&&(!find); r++){

                if (resourcesOwned.get(r).getType().equals(resource.getType())){
                    resourcesOwned.remove(r);
                    i++;
                    find = true;
                }
            }
            find = false;
        }
        if (requirements.size()==i)
            return true;
        else
            return false;

    }

    @Override
    public String toString(){
        return "BuyDiscount";
    }


}
