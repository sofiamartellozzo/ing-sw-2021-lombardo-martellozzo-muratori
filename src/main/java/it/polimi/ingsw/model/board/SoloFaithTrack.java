package it.polimi.ingsw.model.board;

/* ILA */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used in the Single Player game and has lorenzoFaithMarker that is the opponent of the player,
 * infact te player has to play against lorenzo
 * */

public class SoloFaithTrack extends FaithTrack implements Serializable {

    private LorenzoFaithMarker lorenzoFaithMarker;

    /**
     * constructor of the class
     * @param pathBox
     * @param popesFavorTiles
     * @param faithMarker
     * @param vaticanSections
     * @param lorenzoFaithMarker
     */
    public SoloFaithTrack(ArrayList<Box> pathBox, ArrayList<PopesFavorTile> popesFavorTiles , FaithMarker faithMarker, ArrayList<VaticanSection> vaticanSections, LorenzoFaithMarker lorenzoFaithMarker)
    {
        super(pathBox,popesFavorTiles,faithMarker, vaticanSections);
        this.lorenzoFaithMarker = lorenzoFaithMarker;
    }

    /**
     * method used to increase the position of Lorenzo's faithMarker
     */

    public void increaseLorenzoPosition() {
        this.lorenzoFaithMarker.increasePosition();
    }

}
