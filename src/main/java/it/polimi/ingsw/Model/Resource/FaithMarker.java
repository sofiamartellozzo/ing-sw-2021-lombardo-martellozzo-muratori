package it.polimi.ingsw.Model.Resource;

public class FaithMarker extends Resource{
    private int position;
    private Color color;
    private TypeResources typeResource;

    @Override
    public void setColor(Color color) {
        super.color=color;
    }

    @Override
    public Color getColor() {
        return super.color;
    }

    @Override
    public void setType(TypeResources typeResource) {
        super.typeResource=typeResource;
    }

    @Override
    public TypeResources getType() {
        return super.typeResource;
    }

    public int getPosition()
    {
        return this.position;
    }
    public void increasePosition(){

    }
}