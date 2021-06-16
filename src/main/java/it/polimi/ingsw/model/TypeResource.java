package it.polimi.ingsw.model;

/**
 * enum that represents all the 7 types of resources
 * LORENZO --> only in solo game (antagonist)
 * BLANK is a particular case --> when a player has to WhiteMarble activated and he takes from the market a white marble,
 *                                the type of that marble will be BLANK
 */
public enum TypeResource {
    COIN, STONE, SERVANT, SHIELD, FAITHMARKER, LORENZO, BLANK;

    @Override
    public String toString() {
        return super.toString();
    }

    public Color getThisColor(){
        switch (this){
            case COIN:
                return Color.YELLOW;
            case STONE:
                return Color.GREY;
            case SERVANT:
                return Color.PURPLE;
            case SHIELD:
                return Color.BLUE;
        }
        return null;
    }
}
