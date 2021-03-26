package it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.TypeResource;

public class Coin extends Resource {
    @Override
    public void setColor(Color color) {
        super.color=color;
    }
    @Override
    public Color getColor() {
        return super.color;
    }

    @Override
    public void setType(TypeResource typeResource) {
     super.typeResource=typeResource;
    }

    @Override
    public TypeResource getType() {
        return super.typeResource;
    }
}
