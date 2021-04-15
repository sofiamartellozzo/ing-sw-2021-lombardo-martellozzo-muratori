package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * TEST PASSED
 * Interface
 * It represents all type of depots
 * */
public interface Depot {

    /**
     * Gets the content of the Depot as an ArrayList of resources.
     * @return
     */
    public ArrayList<Resource> getResources();

    /**
     * Adds resources in the depot
     * @param  -> The resources you want to put in the depot
     */
    public void addResources(int num,Resource resource) throws InvalidActionException;

    /**
     * Removes resources from the depot
     * @param num -> The number of resources you want to remove from the depot
     */
    public void removeResources(int num) throws InvalidActionException;

    /**
     * Puts the resource parameter in the depot
     * Resources must be of the same type
     * @param resource -> The resource you want to put in the depot
     * @throws InvalidActionException
     */
    public void addResource(Resource resource) throws InvalidActionException;

    /**
     * Removes a resource from the depot
     * @throws InvalidActionException
     */
    public void removeResource() throws InvalidActionException;

    /**
     * Checks if the depot is full.
     * @return
     */
    public boolean isFull();

    /**
     * Get the type of resources contained in the depot.
     * @return
     */
    public TypeResource getType();

    /**
     * Get how many resources can contain the depot.
     * @return
     */
    public int getSize();




}