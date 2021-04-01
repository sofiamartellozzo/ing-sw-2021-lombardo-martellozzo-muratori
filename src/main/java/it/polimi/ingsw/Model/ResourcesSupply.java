package it.polimi.ingsw.Model;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/* ILA */

public class ResourcesSupply {

    private final ArrayList<Cell> content;

    //constructor of the class

    public ResourcesSupply(ArrayList<Cell> content) {
        this.content = content;
    }

    // Getter methods

    public ArrayList<Cell> getContent(Cell cell) {
        return content;
    }

    /*this method gives to the player the resources he asked*/
    public Resource returnResourceAsked(Resource resource) throws IllegalAccessException {

        /*for (Cell cell : content) {
            if (cell.getResources().equals(resource)) {
                return cell.getResources();
            }
            else throw new IllegalAccessException(" Error input resource not valid! ");
        }*/
        return resource;
    }

}
