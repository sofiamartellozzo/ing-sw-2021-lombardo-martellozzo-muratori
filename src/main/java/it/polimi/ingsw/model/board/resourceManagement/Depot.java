package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * Interface
 * It represents all type of depots.
 * */
public interface Depot {

    /**
     * Getter Method
     * @return -> The resources contained by the Depot
     */
    public ArrayList<Resource> getResources();

    /**
     * Adds "n" resources of "resource".
     * @param n -> The number of resource you want to put in
     * @param resource -> The resource you want to put in
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void addResources(int n,Resource resource) throws InvalidActionException;

    /**
     * Removes "n" resources from the depot.
     * @param n -> The number of resources you want to remove
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResources(int n) throws InvalidActionException;

    /**
     * Adds the "resource" in the depot.
     * @param resource -> The resource you want to put in (SHIELD, STONE, COIN, SERVANT)
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void addResource(Resource resource) throws InvalidActionException;

    /**
     * Removes a resource from the depot.
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResource() throws InvalidActionException;

    /**
     * @return -> True if the depot is full, else false.
     */
    public boolean isFull();

    /**
     * Getter Method
     * @return -> The content type of the depot.
     */
    public TypeResource getType();

    /**
     * @return -> How many resource the depot can contain.
     */
    public int getSize();

    /**
     * Getter Method
     * @return -> The number of the "floor"/"depot"
     */
    public int getFloor();


}