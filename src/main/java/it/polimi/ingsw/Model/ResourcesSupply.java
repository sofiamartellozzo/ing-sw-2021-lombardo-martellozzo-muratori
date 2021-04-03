package it.polimi.ingsw.Model;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/* ILA */

/* SINGLETON: because I have to instance this class only once,
and the next time
he has to give me back the same class*/

public class ResourcesSupply {

    private final ArrayList<Cell> content;
    private static ResourcesSupply resourcesSupply = null;

    /**
     * constructor of the class
     * @param content
     */

    private ResourcesSupply(ArrayList<Cell> content) {
        this.content = content;
    }

    public static ResourcesSupply getInstance(ArrayList<Cell> content){
        if(resourcesSupply == null)
        {
            resourcesSupply = new ResourcesSupply(content);
        }
        return resourcesSupply;
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
