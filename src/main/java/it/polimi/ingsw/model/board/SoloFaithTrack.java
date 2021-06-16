package it.polimi.ingsw.model.board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used in the Single Player game and has lorenzoFaithMarker that is the opponent of the player,
 * so the player has to play against lorenzo
 * */

public class SoloFaithTrack extends FaithTrack implements Serializable {

    private LorenzoFaithMarker lorenzoFaithMarker;

    /**
     * constructor of the class
     * @param pathBox --> all the boxes composing the Track
     * @param popesFavorTiles --> 3 popes that can be active or inactive
     * @param faithMarker --> represents the position of the player in Faith Track
     * @param vaticanSections --> special zone on the faith track (3 sections)
     * @param lorenzoFaithMarker --> represents the position of the antagonist
     */
    public SoloFaithTrack(ArrayList<Box> pathBox, ArrayList<PopesFavorTile> popesFavorTiles , FaithMarker faithMarker, ArrayList<VaticanSection> vaticanSections, LorenzoFaithMarker lorenzoFaithMarker)
    {
        super(pathBox,popesFavorTiles,faithMarker, vaticanSections);
        this.lorenzoFaithMarker = lorenzoFaithMarker;
    }

    public LorenzoFaithMarker getLorenzoFaithMarker(){return lorenzoFaithMarker;}

    /**
     * method used to increase the position of Lorenzo's faithMarker
     */

    public void increaseLorenzoPosition() {
        lorenzoFaithMarker.increasePosition();
        int section = checkInvokeVaticanReport();
        if (section != 0){
            doVaticanReport(section);
        }
    }

}
