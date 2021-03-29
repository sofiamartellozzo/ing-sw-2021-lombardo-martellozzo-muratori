package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;

import java.util.ArrayList;

/*
 * GIANLUCA
 * It represents the depots made by the special ability "SpecialDepot",
 * the size is always 2, so it doesn't need a "size" attribute as RealDepot class.
 * Attributes:
 * resources -> indicates what and how many resources are in the depot;
 * content -> indicates what type of resource can be add in the depot,
 * it is specified by the special ability;
 * depot -> indicates which depot (in case of two ability, they can be two).
 * */



public class AbilityDepot implements Depot{
    private ArrayList<Resource> resources;
    private final TypeResource content;
    private final int depot;

    public AbilityDepot(TypeResource content, int depot) {
        this.resources = new ArrayList<>();
        this.content = content;
        this.depot = depot;
    }

    /*
     * It adds at the end in the ArrayList one resource,
     * after checking if the depot isn't full
     * and checking if the resource we want to put
     * into respects the TypeResource content attribute.
     * If one of this condition isn't satisfied
     * the method throws the relative Exception.
     * */
    @Override
    public void putResource(Resource resource) {
        if(resources.size()>=2){
            //throws Exception;
        }
        if(resource.getType().equals(content)){
            //throws Exception;
        }
        resources.add(resource);
    }

    /*
     * It removes at the end in the ArrayList one resource,
     * after checking if the depot isn't empty, else throws Exception
     * */
    @Override
    public void removeResource() {
        if(resources.isEmpty()){
            //throws Exception
        }
        resources.remove(resources.size());
    }
}
