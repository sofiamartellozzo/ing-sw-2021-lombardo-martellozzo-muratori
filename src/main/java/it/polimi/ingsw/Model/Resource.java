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
            case BLUE:
                this.typeResource = TypeResource.SHIELD;
            case YELLOW:
                this.typeResource = TypeResource.COIN;
            case PURPLE:
                this.typeResource = TypeResource.SERVANT;

        }
    }
    public TypeResource getType(){
        return this.typeResource;
    }
}
