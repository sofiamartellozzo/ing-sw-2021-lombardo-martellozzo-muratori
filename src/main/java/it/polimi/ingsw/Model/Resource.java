package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.TypeResource;

import java.util.InvalidPropertiesFormatException;

/*
SOFIA
 */

public class Resource{

    private final Color color;
    private TypeResource typeResource;

    /* constructor */
    public Resource(Color color){
        this.color=color;
        setType();
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
            default:
                throw new IllegalArgumentException("Color not allowed for create a Resource");
        }
    }
    public TypeResource getType(){
        return this.typeResource;
    }
}
