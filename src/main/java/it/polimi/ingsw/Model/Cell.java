package it.polimi.ingsw.Model;

/* ILA */
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
     * method that create a new Resource using the Enum TypeResource
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
     * because the resources are limitless I don't have to control the lenght of the ArrayList before removing them
     * @param resource
     * @return
     */

    public Resource askResource(Resource resource) {
        return createResource(this.type);
    }


}
