package it.polimi.ingsw.Model.market;

public class MarketStructure {
    private Marble[][] structure;
    private Marble slide;

    // Constructor
    public MarketStructure(Marble[][] structure, Marble slide) {
        this.structure = structure;
        this.slide = slide;
    }

    public void rowMoveMarble(int row){};
    public void columnMoveMarble(int column){};
}
