package it.polimi.ingsw.Model;

public class FaithMarker extends Resource{

    private int position;
    private Color color;

    //constructor of the class

    public FaithMarker(Color color,TypeResource typeResource){
        super(color,typeResource);
    }

    // a method to increase the position and move the FaithMarker

    public void increasePosition(){ this.position=getPosition()+1; }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

}
