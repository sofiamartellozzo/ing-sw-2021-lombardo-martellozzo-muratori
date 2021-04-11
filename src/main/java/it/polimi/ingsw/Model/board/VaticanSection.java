package it.polimi.ingsw.Model.board;

import java.util.ArrayList;

/**
 * GIANLUCA
 * TEST PASSED
 * It represents the Vatican Section.
 */
public class VaticanSection {
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
     * @return
     */
    public int getWhichVaticanSection() {
        return whichVaticanSection;
    }

    /**
     * Getter Method
     * @return
     */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    /**
     * Getter Method
     * @return
     */
    public PopesFavorTile getPopesFavorTile() {
        return popesFavorTile;
    }
}
