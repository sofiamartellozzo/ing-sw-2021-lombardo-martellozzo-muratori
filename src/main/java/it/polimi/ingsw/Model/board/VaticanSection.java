package it.polimi.ingsw.Model.board;

import java.util.ArrayList;

/**
 * GIANLUCA
 * It represents the Vatican Section.
 * Attributes:
 * whichVaticanSection -> Indicates which of the three section is
 * boxes -> It indicates which boxes are part of the section
 * popesFavorTile -> The relative
 */
public class VaticanSection {
    private final int whichVaticanSection;
    private final ArrayList<Box> boxes;
    private final PopesFavorTile popesFavorTile;

    /**
     * Constructor
     * @param whichVaticanSection
     * @param boxes
     * @param popesFavorTile
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
