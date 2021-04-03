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
     *
     * @param player
     * @throws InvalidActionException
     */
    @Override
    public void activeAbility(Player player) throws InvalidActionException {
        super.activeAbility(player);
        if(player.getBuyCard().toString().equals("Buy")) {
            BuyDiscount buyDiscount = new BuyDiscount();
            buyDiscount.addResourceWithDiscount(this.getResource());
            player.setBuyCard(buyDiscount);
        }else if (player.getBuyCard().toString().equals("BuyDiscount")){
            player.getBuyCard().buyCard(1, 1, new PlayerTurn(player,),1);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
