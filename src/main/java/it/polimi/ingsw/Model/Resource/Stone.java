package it.polimi.ingsw.Model.Resource;

public class Stone extends Resource {
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
}
