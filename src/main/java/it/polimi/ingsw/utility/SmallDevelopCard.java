package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.Color;

import java.io.Serializable;

/**
 * this class represent a generic Development Card
 * with only a specific color and level
 * used as a requirements in Leader Card
 */
public class SmallDevelopCard  {
    private Color color;
    private int level;

    public SmallDevelopCard(Color color, int level) {
        this.color = color;
        this.level = level;
    }

    public Color getColor() {
        return color;
    }

    /**
     * the level can be 0 if is not specify witch level is required the card
     * @return
     */
    public int getLevel() {
        return level;
    }
}
