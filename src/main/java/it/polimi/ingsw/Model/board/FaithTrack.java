package it.polimi.ingsw.Model.board;
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
     * this method returns the position of the faithMarker in the track (a number)
     * @return
     */
    public int getPositionFaithMarker() {
        return faithMarker.getPosition();
    }

    // Setter method for testing
    public void setFaithMarker() {faithMarker.setPosition(3);}


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
     * method used to check if the FaithMarker is in a PopeBox that hasn't been activated yet
     * @return
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
     * method used to do the vatican report and so control if the FaithMarker is inside a vatican section or not
     * @param section
     */

    public void doVaticanReport (int section)
    {
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
     * Take the Victory points of the box in which the faithMarker is plus the points of the popesFavorTiles,
     * so you have all the points of the FaithTrack
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
     * with this method you increase the position of your faithMarker in your faithTrack
     */

    public void increasePosition() {
        this.faithMarker.increasePosition();
    }

}
