package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.TypeResource;

public class Resource{

    private Color color;
    private TypeResource typeResource;

    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return this.color;
    }
    public void setType(TypeResource typeResource){
        this.typeResource = typeResource;
    }
    public TypeResource getType(){
        return this.typeResource;
    }
}
