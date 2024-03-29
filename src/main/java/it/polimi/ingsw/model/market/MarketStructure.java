package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TypeResource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represents the structure of the market (a matrix of 3x4 Marbles)
 * plus the slide marble
 */
public class MarketStructure implements Serializable {

    private Marble[][] structure; //row,column
    private Marble slide;

    /**
     * Constructor of the class
     * @param structure
     * @param slide
     */
    public MarketStructure(Marble[][] structure, Marble slide) {
        this.structure = structure;
        this.slide = slide;
    }


    public Marble[][] getStructure() {
        return structure;
    }

    public Marble getSlide() {
        return slide;
    }

    /**
     * return a marble in a specific place inside the Market
     * @param row
     * @param column
     * @return
     */
    public Marble getMarble(int row, int column){return structure[row][column];}

    private void setSlide(Marble newSlide){
        this.slide = newSlide;
    }

    public ArrayList<TypeResource> rowMoveMarble(int row, Player player) throws InvalidActionException {
        ArrayList<TypeResource> resourceFromMarket = new ArrayList<>();
        TypeResource resource = null;
        for (int i=0; i<4; i++){
            resource = this.structure[row][i].choose(player);
            if (resource!=null){
                resourceFromMarket.add(resource);

            }
        }
        Marble newSlide = this.structure[row][0];
        Marble oldSlide = this.slide;
        setSlide(newSlide);
        this.structure[row][0]= this.structure[row][1];
        this.structure[row][1]= this.structure[row][2];
        this.structure[row][2]= this.structure[row][3];
        this.structure[row][3]= oldSlide;

        return resourceFromMarket;

    }
    public ArrayList<TypeResource> columnMoveMarble(int column, Player player) throws InvalidActionException{
        ArrayList<TypeResource> resourceFromMarket = new ArrayList<>();
        TypeResource resource = null;
        for (int i=0; i<3; i++){
            resource = this.structure[i][column].choose(player);
            if (resource!=null){
                resourceFromMarket.add(resource);

            }
        }
        Marble newSlide = this.structure[0][column];
        Marble oldSlide = this.slide;
        setSlide(newSlide);
        this.structure[0][column] = this.structure[1][column];
        this.structure[1][column] = this.structure[2][column];
        this.structure[2][column] = oldSlide;

        return resourceFromMarket;
    }


}
