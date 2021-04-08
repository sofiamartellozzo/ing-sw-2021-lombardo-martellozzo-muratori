package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.*;

/**
 * GIANLUCA
 * It refers to the ability of the Discount when the relative Player
 * buy a Development Card
 */

public class Discount extends SpecialAbility{
    /**
     * Constructor
     * @param resource
     */
    public Discount(Resource resource) {
        super(resource);
    }

    /**
     * Gets the BuyCard object from the player, verifies the type.
     * If "Buy", creates a new BuyDiscount object, add the resource discount in it and
     * set the object in the BuyCard of the Player.
     * Else if "BuyDiscount", it means the BuyDiscount object is already created,
     * so using the casting after the control I get the BuyDiscount object and add
     * the new resource discount in it.
     * @param player
     * @throws InvalidActionException
     */
    @Override
    public void activeAbility(Player player) throws InvalidActionException {
        super.activeAbility(player);
        BuyCard buyCard = player.getBuyCard();
        if(buyCard.toString().equals("Buy")) {
            BuyDiscount buyDiscount = new BuyDiscount();
            buyDiscount.addResourceWithDiscount(this.getResource());
            player.setBuyCard(buyDiscount);
        }else if (buyCard.toString().equals("BuyDiscount")){
            BuyDiscount buyDiscount= (BuyDiscount) player.getBuyCard();
            buyDiscount.addResourceWithDiscount(this.getResource());
        }
    }

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
