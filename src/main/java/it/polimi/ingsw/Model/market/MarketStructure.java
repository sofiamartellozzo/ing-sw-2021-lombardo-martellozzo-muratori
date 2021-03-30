package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Player;

/*
 * SOFIA
 * */
public class MarketStructure {
    private Marble[][] structure; //row,column
    private Marble slide;

    /*Constructor*/
    public MarketStructure(Marble[][] structure, Marble slide) {
        this.structure = structure;
        this.slide = slide;
    }

    private void setSlide(Marble newSlide){
        this.slide = newSlide;
    }

    public void rowMoveMarble(int row, Player player){
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
    public void columnMoveMarble(int column, Player player){
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
