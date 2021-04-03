package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;
import it.polimi.ingsw.Exception.*;

import java.util.ArrayList;
/**
 * GIANLUCA
 * Implements Depot interface.
 * It represents the depots made by the special ability "SpecialDepot",
 * the size is always 2, so it doesn't need a "size" attribute as the RealDepot class.
 * resources -> indicates what and how many resources are in the depot;
 * */
public class AbilityDepot implements Depot{
    private ArrayList<Resource> resources;
    private final TypeResource content;
    private final int depot;

    /**
     * Constructor
     * Initializing ArrayList<Resource> resource
     * @param content -> Indicates what type of resource can be add in the depot, it is specified by the special ability
     * @param depot -> Indicates which depot (in case of two ability, they can be two)
     */
    public AbilityDepot(TypeResource content, int depot) {
        this.resources = new ArrayList<>();
        this.content = content;
        this.depot = depot;
    }

    /**
     * Getter Method
     * @return -> The resources contained by the Depot
     */
    @Override
    public ArrayList<Resource> getResources() {
        return resources;
    }

    /**
     * It adds at the end in the ArrayList one resource,
     * after checking if the depot isn't full
     * and checking if the resource we want to put
     * into respects the TypeResource content attribute.
     * If one of this condition isn't satisfied
     * the method throws the relative Exception.
     * @param resource -> The resource you want to put in (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException
     */
    @Override
    public void addResource(Resource resource) throws InvalidActionException {
        if(resources.size()>=2) throw new InvalidActionException("The depot is full!");
        if(!resource.getType().equals(content)) throw new InvalidActionException("The resource you want to put in isn't the same type of the content!");
        resources.add(resource);
    }

    /**
     * It removes at the end in the ArrayList one resource,
     * after checking if the depot isn't empty, else throws Exception.
     * @throws InvalidActionException
     */
    @Override
    public void removeResource() throws InvalidActionException {
        if(resources.isEmpty()) throw new InvalidActionException("The depot is empty!");
        resources.remove(resources.size());
    }
}
