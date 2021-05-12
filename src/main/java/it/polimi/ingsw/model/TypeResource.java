package it.polimi.ingsw.model;

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
