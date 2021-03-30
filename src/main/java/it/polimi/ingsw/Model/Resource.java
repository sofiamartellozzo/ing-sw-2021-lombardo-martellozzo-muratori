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
    }

    public Color getColor(){
        return this.color;
    }

    /**
     * set the type of the resources(class) based on wich color it is
     * @throws InvalidPropertiesFormatException
     */
    public void setType() throws InvalidPropertiesFormatException{
        switch (this.color){
            case RED:
                this.typeResource = TypeResource.FAITHMARKER;
            case BLUE:
                this.typeResource = TypeResource.SHIELD;
            case YELLOW:
                this.typeResource = TypeResource.COIN;
            case PURPLE:
                this.typeResource = TypeResource.SERVANT;
            default:
                throw new InvalidPropertiesFormatException("color not allowed for Resources!");
        }
    }
    public TypeResource getType(){
        return this.typeResource;
    }
}
