package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represents the generic action of buying a card from the Dev Table (without discounts)
 */
public class Buy implements BuyCard, Serializable {
    @Override
    public void buyCard(int row, int column, BoardManager boardManager, Player player, int selectedCardSpace) throws InvalidActionException, CardSpaceException {
        //when the player decides to puy a card
        /* took the card payment.. then remove the card from the table ecc..*/
        DevelopmentCard cardBought = boardManager.getDevelopmentCardTable().takeCard(row,column);
        ArrayList<Resource> cost = cardBought.getCost();
        PersonalBoard playerBoard = player.getGameSpace();
        if (checkBeforeBuy(cardBought, player)){
            try {
                playerBoard.getCardSpaces().get(selectedCardSpace).addCard(cardBought);
                playerBoard.getResourceManager().removeResourcesFromBoth(cost);
                boardManager.getDevelopmentCardTable().removeCard(row, column);
            } catch (CardSpaceException e) {
                throw new CardSpaceException("");
            }

        }
        else{
            throw new InvalidActionException("Not enought resources for buy this Development Card!");
        }

    }


    /**
     * this method checks if the players has the resources to buy a specific card
     * @param card --> that the player wants to buy
     * @param player --> current of the turn
     * @return true --> the player has the resources
     */
    private boolean checkBeforeBuy(DevelopmentCard card, Player player){
        ArrayList<Resource> requirements = new ArrayList<>();
        ArrayList<Resource> resourcesOwned = new ArrayList<>();
        requirements = card.getCost();
        resourcesOwned = player.getGameSpace().getResourceManager().getResources();

        int i = 0;
        boolean find = false;
        for (Resource resource: requirements){
            for (int r=0; (!find)&&(!resourcesOwned.isEmpty())&&(resourcesOwned.size()>0)&&(r< resourcesOwned.size()); r++){

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
    public String toString() {
        return "Buy";
    }
}
