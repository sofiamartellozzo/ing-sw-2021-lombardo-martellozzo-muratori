package it.polimi.ingsw.model;
import java.util.ArrayList;

/* ILA */

/** SINGLETON: because I have to instance this class only once,
    and the next time he has to give me back the same class
 */

/** in this class we have an Arraylist of 4 Cells containing the four different resources, one foreach Cell*/

public class ResourcesSupply {

    private final ArrayList<Cell> content;
    private static ResourcesSupply resourcesSupply = null;

    /**
     * constructor of the class
     * @param content
     */

    public ResourcesSupply(ArrayList<Cell> content) {
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

    /** @param resource
     * this method gives to the player the resource he asked
     * */
    public Resource returnResourceAsked(Resource resource) throws IllegalAccessException {
        return resource;
    }

}
