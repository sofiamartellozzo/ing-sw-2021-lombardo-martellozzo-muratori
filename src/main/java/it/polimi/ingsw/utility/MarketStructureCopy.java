package it.polimi.ingsw.utility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.market.Marble;

import java.io.Serializable;

/**
 * this class represents a model Market Structure with the Slide Marble
 * this class is NOT USED NOW!!
 */
public class MarketStructureCopy implements Serializable {

    private Marble[][] marketStructure;
    private Marble slide;

    public MarketStructureCopy(Marble slideMarble)
    {
        marketStructure = new Marble[3][4];
        slide = slideMarble;
    }


    /**
     * this method fill the Market structure
     * @param market
     */
    public void storeMarket(Marble[][] market,Marble slideMarble){
        marketStructure = market;
        slide = slideMarble;
    }

    /**
     * this method returns a single Marble taken from the Market Structure
     * @param row
     * @param column
     * @return
     */
    public Marble getMarble(int row, int column){return marketStructure[row][column];}

    public Marble getSlide() { return slide; }
}
