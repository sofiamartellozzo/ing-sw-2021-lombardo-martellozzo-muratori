package it.polimi.ingsw.Model.Resource;


public abstract class Resource {

    protected Color color;
    protected TypeResources typeResource;

    public abstract void setColor(Color color);
    public abstract Color getColor();
    public abstract void setType(TypeResources typeResource);
    public abstract TypeResources getType();
}
