package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * It represents the StrongBox in the PersonalBoard, it can contain infinite resources.
 * Attributes:
 * numberResources -> it's the number of resources that the StrongBox contains in that moment;
 * content -> the content in resources of the StrongBox.
 * */

public class StrongBox{
    private int numberResources;
    private ArrayList<Resource> content;

    /**
     * Constructor
     */
    public StrongBox() {
        this.numberResources = 0;
        this.content = new ArrayList<>();
    }

    /**
     * Getter Method
     * @return -> The number of resources
     */
    public int getNumberResources() {
        return numberResources;
    }

    /**
     * Getter Method
     * @return -> The list of the contained
     */
    public ArrayList<Resource> getContent() {
        return content;
    }

    /**
     * It adds all the resources given in input in the strongbox, increasing the "numberResources".
     * @param resources
     */
    public void addResources(ArrayList<Resource> resources){
        content.addAll(resources);
        numberResources = numberResources+resources.size();
    }

    /**
     * It removes some resource given in input from the strongbox, decreasing the "numberResources".
     * If the strongbox is empty or doesn't contain some resources which are given in input, it throws an Exception.
     * @param resources
     * @throws InvalidActionException
     */
    public void removeResources(ArrayList<Resource> resources) throws InvalidActionException {
        if(content.isEmpty()) throw new InvalidActionException("The strongbox is empty");
        if(!content.containsAll(resources)) throw new InvalidActionException("The strongbox doesn't contain some resources you want to remove!");
        content.removeAll(resources);
        numberResources = numberResources - resources.size();
    }
}