package it.polimi.ingsw.model;

/* ILA */

/**
 * this class creates the Cell of the resourcesSupply, composed by a specific type of resource
 */
public class Cell {

    private final Resource resource;
    private final TypeResource type;

    /**
     * constructor of the class
     * @param type
     */

    public Cell(TypeResource type){
        this.resource = createResource(type);
        this.type=type;
    }

    /**
     * method that creates a new Resource using the Enum TypeResource
     * @param type
     * @return
     */

    private Resource createResource(TypeResource type){
        switch (type){
            case COIN:
                return new Resource(Color.YELLOW);
            case STONE:
                return new Resource(Color.GREY);
            case SERVANT:
                return new Resource(Color.PURPLE);
            default:
                return new Resource(Color.BLUE);
        }
    }

    // Getter methods
    public Resource getResources() { return resource; }
    public TypeResource getType() { return type; }

    /**
     * method created to give to the player the resource of the type he asked
     * because the resources are limitless we don't have to control the length of the ArrayLists before removing them
     * @return
     */

    public Resource askResource() {
        return createResource(this.getType());
    }

}
