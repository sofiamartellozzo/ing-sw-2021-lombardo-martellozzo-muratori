package it.polimi.ingsw.Model;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ResourcesSupply {
    private ArrayList<Cell> content;

    //constructor of the class

    public ResourcesSupply(ArrayList<Cell> content){
        this.content=content;
    }

    public ArrayList<Cell> getContent(Cell cell) {
        return content;
    }

    public void setContent(ArrayList<Cell> content) {
        this.content = content;
    }

    /*this method gives to the player the resources he asked*/
    public Resource returnResourceAsked(Resource resource){
        Cell c = getCell(resource);
        return c.getResources();
    }

    private Cell getCell(Resource resource){
        for (Cell cell: content) {
            if (cell.getResources().equals(resource))
                return cell;
        }
    }

}
