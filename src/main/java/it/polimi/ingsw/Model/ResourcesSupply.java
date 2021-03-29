package it.polimi.ingsw.Model;
import java.util.ArrayList;
import it.polimi.ingsw.Model.CellType;

public class ResourcesSupply {
    private ArrayList<Cell> content;

    //constructor of the class

    public ResourcesSupply(ArrayList<Cell> content){
        this.content=content;
    }

    public ArrayList<Cell> getContent(CellType cell) {
        return content;
    }

    public void setContent(ArrayList<Cell> content) {
        this.content = content;
    }

    /*this method gives to the player the resources he asked*/
    public Resource returnResourceAsked(Resource resource){
        return resource;
    }

    /*this method is used to put back in the resource supply a resource*/
    public void reInsertResources(Resource resource){ }
}
