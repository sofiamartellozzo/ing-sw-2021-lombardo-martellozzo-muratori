package it.polimi.ingsw.model.board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * It represents the Vatican Section, a section composed by some simpleBoxes, some goldBoxes and only one popeBox (the last one)
 */
public class VaticanSection implements Serializable {
    private final int whichVaticanSection;
    private final ArrayList<Box> boxes;
    private final PopesFavorTile popesFavorTile;

    /**
     * Constructor
     * @param whichVaticanSection -> Indicates which of the three section is
     * @param boxes -> It indicates which boxes are part of the section
     * @param popesFavorTile -> The relative Pope's Favor Tile
     */
    public VaticanSection(int whichVaticanSection, ArrayList<Box> boxes, PopesFavorTile popesFavorTile) {
        this.whichVaticanSection = whichVaticanSection;
        this.boxes = boxes;
        this.popesFavorTile = popesFavorTile;
    }

    /**
     * Getter Method
     * @return --> number corresponding to which vatican section (1-2-3)
     */
    public int getWhichVaticanSection() {
        return whichVaticanSection;
    }

    /**
     * Getter Method
     * @return --> list of boxes in that vatican section
     */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    /**
     * Getter Method
     * @return --> pope favor tile of a specific vatican section
     */
    public PopesFavorTile getPopesFavorTile() {
        return popesFavorTile;
    }
}
