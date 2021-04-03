package it.polimi.ingsw.Model.board;

/* ILA */

import java.util.ArrayList;

/*This class is used in a Single Player game and has lorenzoFaithMarker that is the opponent of the player*/
public class SoloFaithTrack extends FaithTrack {

    private LorenzoFaithMarker lorenzoFaithMarker;
    public SoloFaithTrack(ArrayList<Box> pathBox, ArrayList<PopesFavorTile> popesFavorTiles , FaithMarker faithMarker, LorenzoFaithMarker lorenzoFaithMarker)
    {
        super(pathBox,popesFavorTiles,faithMarker);
        this.lorenzoFaithMarker = lorenzoFaithMarker;
    }

    // method used to increase the position of Lorenzo's faithMarker
    public void increaseLorenzoPosition() {
        this.lorenzoFaithMarker.increasePosition();
    }

}
