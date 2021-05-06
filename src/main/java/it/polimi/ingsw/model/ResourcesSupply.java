package it.polimi.ingsw.model;
import java.util.ArrayList;

/* ILA */

/** SINGLETON: because I have to instance this class only once,
    and the next time he has to give me back the same class
 */

/**
 * in this class we have an Arraylist of 4 Cells containing the four different resources, one foreach Cell
 * cell 1--> coin
 * cell 2--> servant
 * cell 3--> stone
 * cell 4--> shield
 * */

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

    public Cell getCell(int cell) {
        return content.get(cell);
    }

    public ArrayList<Cell> getContent() {
        return content;
    }

    /** @param resource
     * this method gives to the player the resource he asked
     * */
    public Resource returnResourceAsked(TypeResource resource) throws IllegalAccessException {
        switch (resource){
            case COIN:
                return getCell(0).getResources();
            case SERVANT:
                return getCell(1).getResources();
            case STONE:
                return getCell(2).getResources();
            case SHIELD:
                return getCell(3).getResources();
        }
        return null;
    }

}
