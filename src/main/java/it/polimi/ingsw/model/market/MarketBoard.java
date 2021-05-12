package it.polimi.ingsw.model.market;

import java.io.Serializable;

/*
 * GIANLUCA
 * */
public class MarketBoard implements Serializable {
    private MarketStructure structure;

    // Constructor
    public MarketBoard(MarketStructure structure) {
        this.structure = structure;
    }

}
