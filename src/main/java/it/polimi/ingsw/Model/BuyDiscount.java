package it.polimi.ingsw.Model;

public class BuyDiscount implements BuyCard{

    /*
    * created by Dicount Ability */

    private Resource resourceWithDiscount;

    /* constructor of the class */
    public BuyDiscount(Resource resourceWithDiscount) {
        this.resourceWithDiscount = resourceWithDiscount;
    }

    @Override
    public void buyCard() {
        //same as buy card but with discount
    }
}
