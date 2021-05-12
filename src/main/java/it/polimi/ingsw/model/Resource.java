package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.InvalidPropertiesFormatException;

/*
SOFIA
 */

public class Resource implements Serializable {

    protected Color color;
    protected TypeResource typeResource;

    /* constructor */
    public Resource(Color color){
        this.color=color;
        setType();
    }
    public Resource(TypeResource type){
        this.typeResource=type;
        setColor();
    }

    public Color getColor(){
        return this.color;
    }

    /**
     * set the type of the resources(class) based on wich color it is
     * @throws InvalidPropertiesFormatException
     */
    private void setType() {
        switch (this.color){
            case RED:
                this.typeResource = TypeResource.FAITHMARKER;
                break;
            case BLUE:
                this.typeResource = TypeResource.SHIELD;
                break;
            case YELLOW:
                this.typeResource = TypeResource.COIN;
                break;
            case PURPLE:
                this.typeResource = TypeResource.SERVANT;
                break;
            case GREY:
                this.typeResource = TypeResource.STONE;
                break;
            case BLACK:
                this.typeResource = TypeResource.LORENZO;
                break;
            default:
                throw new IllegalArgumentException("Color not allowed for create a Resource!");
        }
    }

    private void setColor() {
        switch (this.typeResource){
            case SHIELD:
                this.color = Color.BLUE;
                break;
            case STONE:
                this.color = Color.GREY;
                break;
            case COIN:
                this.color = Color.YELLOW;
                break;
            case SERVANT:
                this.color = Color.PURPLE;
                break;
            case FAITHMARKER:
                this.color = Color.RED;
                break;
            case LORENZO:
                this.color = Color.BLACK;
                break;
            default:
                throw new IllegalArgumentException("Color not allowed for create a Resource!");
        }
    }
    public TypeResource getType(){
        return this.typeResource;
    }
}
