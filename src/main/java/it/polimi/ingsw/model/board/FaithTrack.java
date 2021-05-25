package it.polimi.ingsw.model.board;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Indicates the path that the player must run across to win.
 * The "faithMarker" attribute indicates the position of the player.
 * The "popesFavorTiles", "pathBox", "vaticanSections" compose the FaithTrack
 */
public class FaithTrack implements Serializable{

    private final FaithMarker faithMarker;
    private final ArrayList<PopesFavorTile> popesFavorTiles;
    private final ArrayList<Box> pathBox;
    private final ArrayList<VaticanSection> vaticanSections;

    /**
     * Constructor
     * @param pathBox -> The structure of the boxes path
     * @param popesFavorTiles -> The three Pope's Favor Tile
     * @param faithMarker -> The faith marker of the player
     * @param vaticanSections -> The references to the vatican sections
     */
    public FaithTrack(ArrayList<Box> pathBox,ArrayList<PopesFavorTile> popesFavorTiles ,FaithMarker faithMarker,ArrayList<VaticanSection> vaticanSections){
        this.faithMarker = faithMarker;
        this.pathBox = pathBox;
        this.popesFavorTiles = popesFavorTiles;
        this.vaticanSections = vaticanSections;
    }

    /**
     * Getter method
     * @return -> The "vaticanSections"
     */
    public ArrayList<VaticanSection> getVaticanSections() { return vaticanSections; }

    /**
     * Getter Method
     * @return -> The "faithMarker"
     */
    public FaithMarker getFaithMarker() { return faithMarker;}

    /**
     * Getter Method
     * @return -> The "popesFavorTiles"
     */
    public ArrayList<PopesFavorTile> getPopesFavorTiles(){return popesFavorTiles;}

    /**
     * Getter Method
     * @return -> The "pathBox"
     */
    public ArrayList<Box> getPathBox() { return pathBox;}

    /**
     * @return -> The position of the player's faith marker
     */
    public int getPositionFaithMarker() {
        return faithMarker.getPosition();
    }

    /**
     * @return -> All the boxes of type "PopeBox" as an ArrayList<PopeBox>
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
     * Check if the player is in a Pope Box not activated yet.
     * @return -> If yes, return Which Vatican Section, else 0.
     */
    public int checkInvokeVaticanReport() {
        for (PopeBox popebox: getPopeBoxes())
        {
            if ((faithMarker.getPosition() == popebox.getNumberBox()) && !popebox.isActivated())
            {
                return popebox.getWhichSection();
            }
        }
        return 0;
    }


    /**
     * After obtaining the section and the relative pope box,
     * set to activates the box and call its vaticanReport method.
     * @param section -> In which Vatican Section activate the Vatican Report
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
     * @return -> All victory points which refer to the box where the position of the FaithMarker is and to the activated
     * Pope's Favor Tiles
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
     * Increase the position of the player's FaithMarker but before check if it is less than 24
     */
    public void increasePosition() {

            faithMarker.increasePosition();
            int section = checkInvokeVaticanReport();
            if (section != 0){
                System.out.println("KKKKKK");
                doVaticanReport(section);
            }

    }

}
