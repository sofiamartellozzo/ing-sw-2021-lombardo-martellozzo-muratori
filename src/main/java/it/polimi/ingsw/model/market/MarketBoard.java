package it.polimi.ingsw.model.market;

import java.io.Serializable;

public class MarketBoard implements Serializable {
    private MarketStructure structure;

    // Constructor
    public MarketBoard(MarketStructure structure) {
        this.structure = structure;
    }

}
