package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.board.resourceManagement.RealDepot;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;
import it.polimi.ingsw.Model.card.DevelopmentCard;

import java.util.ArrayList;
import java.util.HashMap;

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
    public String toString() {
        return "Buy";
    }
}
