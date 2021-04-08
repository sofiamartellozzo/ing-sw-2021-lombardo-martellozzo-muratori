package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Player;

/*
 * SOFIA
 *
 * SINGLETON: istanziated only once, it's unique*/
public class MarketStructure {
    private Marble[][] structure; //row,column
    private Marble slide;

    private static MarketStructure marketStructure = null;

    /*Constructor*/
    private MarketStructure(Marble[][] structure, Marble slide) {
        this.structure = structure;
        this.slide = slide;
    }

    public static MarketStructure getInstance(Marble[][] structure, Marble slide){
        if (marketStructure == null)
            marketStructure = new MarketStructure(structure, slide);
        return marketStructure;
    }

    public Marble[][] getStructure() {
        return structure;
    }

    public Marble getSlide() {
        return slide;
    }

    private void setSlide(Marble newSlide){
        this.slide = newSlide;
    }

    public void rowMoveMarble(int row, Player player) throws InvalidActionException {
        for (int i=0; i<4; i++){
            this.structure[row][i].choose(player);
        }
        Marble newSlide = this.structure[row][0];
        Marble oldSlide = this.slide;
        setSlide(newSlide);
        this.structure[row][0]= this.structure[row][1];
        this.structure[row][1]= this.structure[row][2];
        this.structure[row][2]= this.structure[row][3];
        this.structure[row][3]= oldSlide;

    }
    public void columnMoveMarble(int column, Player player) throws InvalidActionException{
        for (int i=0; i<3; i++){
            this.structure[i][column].choose(player);
        }
        Marble newSlide = this.structure[0][column];
        Marble oldSlide = this.slide;
        setSlide(newSlide);
        this.structure[0][column] = this.structure[1][column];
        this.structure[1][column] = this.structure[2][column];
        this.structure[2][column] = oldSlide;
    }
}
