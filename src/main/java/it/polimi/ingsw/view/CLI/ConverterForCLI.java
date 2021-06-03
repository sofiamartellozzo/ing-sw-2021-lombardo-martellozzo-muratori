package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

/**
 * class created to convert from string to Color/TypeResource
 */
public class ConverterForCLI {
    /**
     * because the player is asked to insert a string that represents the color of the resource he wants,
     * this method converts the color written by the player to a real Color
     *
     * @param resourceColor
     * @return
     */
    public Color getColorFromString(String resourceColor) {
        switch (resourceColor) {
            case "YELLOW":
                return Color.YELLOW;
            case "BLUE":
                return Color.BLUE;
            case "GREY":
                return Color.GREY;
            case "PURPLE":
                return Color.PURPLE;
        }

        throw new IllegalArgumentException(" Error, color not valid ");
    }

    /**
     * auxiliary method that creates the color basing on the type
     *
     * @param typeRes
     * @return
     */
    public Color getColorFromType(String typeRes) {
        switch (typeRes) {
            case "SHIELD":
                return Color.BLUE;
            case "COIN":
                return Color.YELLOW;
            case "SERVANT":
                return Color.PURPLE;
            case "STONE":
                return Color.GREY;
        }
        throw new IllegalArgumentException("Error, type not valid!");
    }

    /**
     * auxiliary method used to create the type from the string
     *
     * @param type
     * @return
     */
    public TypeResource getTypeFromString(String type) {

        switch (type) {
            case "COIN":
                return TypeResource.COIN;
            case "SHIELD":
                return TypeResource.SHIELD;
            case "STONE":
                return TypeResource.STONE;
            case "SERVANT":
                return TypeResource.SERVANT;
        }
        throw new IllegalArgumentException("Error, type not valid!");
    }

    /**
     * auxiliary method that creates the resource basing on the color
     *
     * @param color
     * @return
     */
    public TypeResource getResourceFromColor(Color color) {
        switch (color) {
            case YELLOW:
                return TypeResource.COIN;
            case BLUE:
                return TypeResource.SHIELD;
            case GREY:
                return TypeResource.STONE;
            case PURPLE:
                return TypeResource.SERVANT;
        }
        throw new IllegalArgumentException("Error, color not valid! ");
    }
}
