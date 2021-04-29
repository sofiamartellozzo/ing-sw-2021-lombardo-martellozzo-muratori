package it.polimi.ingsw.model.board;
import java.util.ArrayList;

/*
* ILARIA
* */

public class FaithTrack {

    private FaithMarker faithMarker;
    private final ArrayList<PopesFavorTile> popesFavorTiles;
    private final ArrayList<Box> pathBox;
    private ArrayList<VaticanSection> vaticanSections;

    /**
     * Constructor of the class
     * @param pathBox
     * @param popesFavorTiles
     * @param faithMarker
     */
    public FaithTrack(ArrayList<Box> pathBox,ArrayList<PopesFavorTile> popesFavorTiles ,FaithMarker faithMarker,ArrayList<VaticanSection> vaticanSections){
        this.faithMarker = faithMarker;
        this.pathBox = pathBox;
        this.popesFavorTiles = popesFavorTiles;
        this.vaticanSections = vaticanSections;
    }

    // Getter methods
    public ArrayList<VaticanSection> getVaticanSections() { return vaticanSections; }
    public FaithMarker getFaithMarker() { return faithMarker;}
    public ArrayList<PopesFavorTile> getPopesFavorTiles(){return popesFavorTiles;}
    public ArrayList<Box> getPathBox() { return pathBox;}

    /**
     * this method returns the position of the faithMarker in the FaithTrack, at the beginning it is set in
     * the first box and during the game the player move it
     * @return
     */
    public int getPositionFaithMarker() {
        return faithMarker.getPosition();
    }

    // Setter method for testing
    public void setFaithMarker() {faithMarker.setPosition(4);}


    /**
     * returns an array of only the popeBoxes from the faithTrack
     * @return
     */
    public ArrayList<PopeBox> getPopeBoxes() {
        ArrayList<PopeBox> popeBoxes = new ArrayList<>();
        for (Box box :pathBox ) {
            if (box.toString().equals("PopeBox"))
            {
               popeBoxes.add((PopeBox) box);
            }
        }
        return popeBoxes;
    }

    /**
     * method used to check if the position of the FaithMarker is in a PopeBox that hasn't been activated yet,
     * in that case it returns the section of the PopeBox in which we have to do the control
     * @return vaticanSection
     */

    public int checkInvokeVaticanReport() {
        for (PopeBox popebox: getPopeBoxes())
        {
            if (faithMarker.getPosition() == popebox.getNumberBox() && !popebox.isActivated())
            {
                return popebox.getWhichSection();
            }
        }
        return 0;
    }

    /**
     * method used to do the vatican report and control if the FaithMarker is up to a vatican section or not, if it is in
     * the vatican section the player will take the points of the relative popesFavorTiles, otherwise not.
     * @param section
     */

    public void doVaticanReport(int section) {
        VaticanSection vaticanSection = vaticanSections.get(section - 1);
        PopeBox popeBox = (PopeBox) vaticanSection.getBoxes().get(vaticanSection.getBoxes().size() - 1);
        popeBox.setActivated(true);

        for (Box box: vaticanSection.getBoxes()) {

            if (faithMarker.getPosition() == box.getNumberBox())
            {
                popeBox.vaticanReport(vaticanSection);
            }
        }
    }

    /**
     * Take the Victory points of the box in which the faithMarker is plus the points of the popesFavorTiles
     * that during the game the player activated with the vatican Reports, so you have all the points of the FaithTrack
     * @return TotPoints + BoxPoints
     */

    public int getAllVictoryPoints(){

        int BoxPoints;
        int TotPoints = 0;

        if(faithMarker.getPosition()==0) { BoxPoints = 0;}

        else {BoxPoints = pathBox.get(getPositionFaithMarker()).getVictoryPoints();}

        for (int i = 0; i < 3; i++)
        {
            TotPoints += popesFavorTiles.get(i).getVictoryPoints();

        }
        return (TotPoints + BoxPoints);
    }

    /**
     * with this method the player increase the position of his faithMarker in the faithTrack
     */

    public void increasePosition() {
        this.faithMarker.increasePosition();
    }

}
