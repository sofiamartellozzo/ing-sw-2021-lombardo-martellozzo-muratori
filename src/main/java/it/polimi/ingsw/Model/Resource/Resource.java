package it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.TypeResource;

public abstract class Resource {

    protected Color color;
    protected TypeResource typeResource;

    public abstract void setColor(Color color);
    public abstract Color getColor();
    public abstract void setType(TypeResource typeResource);
    public abstract TypeResource getType();
}
