package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;

import java.util.ArrayList;

public class CellType implements Cell {

    private ArrayList<Resource> resources;
    private TypeResource type;

    //constructor of the class
    public CellType(ArrayList<Resource> resources,TypeResource type){
        this.resources=resources;
        this.type=type;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    public TypeResource getType() {
        return type;
    }

    public void setType(TypeResource type) {
        this.type = type;
    }

    /* because the resources are limitless I don't have to control the lenght of the ArrayList before removing them*/
    @Override
    public void removeResource(Resource resource) {
        getResources().remove(resource);
    }

    @Override
    public void addResource(Resource resource) {
        getResources().add(resource);
    }
}
