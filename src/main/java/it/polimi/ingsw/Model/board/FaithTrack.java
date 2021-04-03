package it.polimi.ingsw.Model.board;
import java.util.ArrayList;

/*
* ILARIA
* */

public class FaithTrack {

    private FaithMarker faithMarker;
    private final ArrayList<PopesFavorTile> popesFavorTiles;
    private final ArrayList<Box> pathBox;

    // Constructor of the class
    public FaithTrack(ArrayList<Box> pathBox,ArrayList<PopesFavorTile> popesFavorTiles ,FaithMarker faithMarker){
        this.faithMarker = faithMarker;
        this.pathBox = pathBox;
        this.popesFavorTiles = popesFavorTiles;
    }

    // Getter methods
    public FaithMarker getFaithMarker() { return faithMarker;}
    public ArrayList<PopesFavorTile> getPopesFavorTiles(){return popesFavorTiles;}
    public ArrayList<Box> getPathBox() { return pathBox;}

    // this method returns the position of the faithMarker in the track (a number)
    public int getPositionFaithMarker() {
        return faithMarker.getPosition();
    }

    // Setter method
    public void setFaithMarker(FaithMarker faithMarker) { this.faithMarker = faithMarker; }

    /* Take the Victory points of the box in which the faithMarker is plus the points of the popesFavorTiles,
     so you have all the points of the FaithTrack */
    public int getAllVictoryPoints(){

        int BoxPoints;
        int TotPoints;

        if(faithMarker.getPosition()==0) { BoxPoints = 0;}
        else BoxPoints = pathBox.get(faithMarker.getPosition()-1).getVictoryPoints();

        TotPoints = popesFavorTiles.get(0).getVictoryPoints();
        TotPoints += popesFavorTiles.get(1).getVictoryPoints();
        TotPoints += popesFavorTiles.get(2).getVictoryPoints();

        return (TotPoints + BoxPoints);
    }

    // with this method you increase the position of your faithMarker in your faithTrack
    public void increasePosition() {
        this.faithMarker.increasePosition();
    }

}
