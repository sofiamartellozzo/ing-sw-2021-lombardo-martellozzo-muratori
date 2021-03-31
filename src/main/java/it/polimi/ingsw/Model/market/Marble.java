package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

/*
 * SOFIA
 * */
public abstract class Marble {
    protected Color color;
    //protected int row;
    //protected int column;

    /* I cannot assign to the marble two attributes of his position in the market, there is one marble in the slide!!
    * REMEMBER TO CHECK IN THE MARKET NOT ILLEGAL POSITION OF THEM!!
    * AND THIS IS ABSTRACT CLASS, NO CONSTRUCTOR ACCEPTED
     *
     *
    /**
     * when the market will be built, set the position of the generic Marble in it
     * it has to be [3][4] so here the method have to check not illegal position given
     *
     * @param column
     * @param row
     *
    public void setPosition(int row, int column) throws IllegalArgumentException{
        if ((row >= 0)&&(row<4)){
            this.row = row;
        }
        else throw new IllegalArgumentException("paramether row not allowed!");
        if ((column >= 0)&&(column<5)){
            this.column = column;
        }
        else
            throw new IllegalArgumentException("paramether column not allowed!");

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    */

    public Color getColor() {
        return color;
    }

    /**
     * method called when the player select that marble from the market
     * based on wich marble this will return the right Resource or move
     * the Faith Marker
     *
     * @param player
     * @return
     */
    public abstract void choose(Player player);
}
