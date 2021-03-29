package it.polimi.ingsw.Model;

public class Cell {

    private final Resource resource;
    private TypeResource type;

    //constructor of the class
    public Cell(TypeResource type){
        this.resource = createResource(type);
        this.type=type;
    }

    private Resource createResource(TypeResource type){
        switch (type){
            case COIN:
                return new Resource(Color.YELLOW,TypeResource.COIN );
            case STONE:
                return new Resource(Color.GREY, TypeResource.STONE);
            case SERVANT:
                return new Resource(Color.PURPLE, TypeResource.SERVANT);
            default:
                return new Resource(Color.BLUE, TypeResource.SHIELD);
        }
    }

    public Resource getResources() {
        return resource;
    }


    public TypeResource getType() {
        return type;
    }


    /* because the resources are limitless I don't have to control the lenght of the ArrayList before removing them*/
    public Resource askResource(Resource resource) {
        return createResource(this.type);
    }


}
