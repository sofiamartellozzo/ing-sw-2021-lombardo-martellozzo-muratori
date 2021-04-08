package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * GIANLUCA
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
     * Puts the resource parameter in the depot
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