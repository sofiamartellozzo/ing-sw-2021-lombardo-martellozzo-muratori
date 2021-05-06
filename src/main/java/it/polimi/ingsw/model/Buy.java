package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

/*
* SOFI*/

public class Buy implements BuyCard {
    @Override
    public void buyCard(int row, int column, BoardManager boardManager, Player player, int selectedCardSpace) throws InvalidActionException{
        //when the player decides to puy a card
        /* took the card payment.. then remove the card from the table ecc..*/
        DevelopmentCard cardBought = boardManager.getDevelopmentCardTable().takeCard(row,column);
        ArrayList<Resource> cost = cardBought.getCost();
        PersonalBoard playerBoard = player.getGameSpace();
        if (checkBeforeBuy(cardBought, player)){
            playerBoard.getResourceManager().removeResourcesFromBoth(cost);
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
    public String toString() {
        return "Buy";
    }
}
