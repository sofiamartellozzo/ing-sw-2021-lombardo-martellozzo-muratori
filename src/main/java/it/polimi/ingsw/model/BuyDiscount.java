package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * created by Dicount Ability
 * this class is used when a player has the Discount Ability activated and so before buying a card from the table,
 * the cost has to be discounted of 1 resource (specified in the card)
 */
public class BuyDiscount implements BuyCard, Serializable {

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
        DevelopmentCard cardBought = boardManager.getDevelopmentCardTable().takeCard(row, column);
        ArrayList<Resource> cost = cardBought.getCost();
        ArrayList<Resource> auxiliarCost = (ArrayList<Resource>) cost.clone();
        PersonalBoard playerBoard = player.getGameSpace();
        /*apply the discount*/
        //cost.removeAll(resourceWithDiscount); --->these are not exacly the same object, I have to remove the one by one
        for (Resource rDiscounted : resourceWithDiscount) {
            int j = auxiliarCost.size();
            boolean found = false;
            for (int i = 0; (i < j) && (!found); i++) {
                Resource resource = auxiliarCost.get(i);
                if (resource.getType().equals(rDiscounted.getType())) {
                    auxiliarCost.remove(resource);
                    found=true;
                }
                i--;
                j--;
            }
        }
        if (checkBeforeBuy(cardBought, player, auxiliarCost)) {
            playerBoard.getResourceManager().removeResourcesFromBoth(auxiliarCost);
            //playerBoard.removeResource(cost.get(1), new RealDepot(1,1));
            playerBoard.getCardSpaces().get(selectedCardSpace).addCard(cardBought);
        } else {
            throw new InvalidActionException("Not enought resources for buy this Development Card!");
        }

    }

    private boolean checkBeforeBuy(DevelopmentCard card, Player player, ArrayList<Resource> requirements) {
        ArrayList<Resource> resourcesOwned = new ArrayList<>();
        //requirements = card.getCost(); ---> I give the cost in input because I applyed the discount in the method above
        resourcesOwned = player.getGameSpace().getResourceManager().getResources();
        int i = 0;
        boolean find = false;
        for (Resource resource : requirements) {
            for (int r = 0; (!resourcesOwned.isEmpty()) && (0 < resourcesOwned.size()) && (!find) && (r < resourcesOwned.size()); r++) {

                if (resourcesOwned.get(r).getType().equals(resource.getType())) {
                    resourcesOwned.remove(r);
                    i++;
                    find = true;
                }
            }
            find = false;
        }
        if (requirements.size() == i)
            return true;
        else
            return false;

    }

    @Override
    public String toString() {
        return "BuyDiscount";
    }


}
