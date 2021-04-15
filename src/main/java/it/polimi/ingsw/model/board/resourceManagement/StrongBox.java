package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * GIANLUCA
 * TEST PASSED
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
        if(!resources.isEmpty()){
            if(content.isEmpty() && !resources.isEmpty()) throw new InvalidActionException("The strongbox is empty");
            if(!checkEnoughResources(resources)) throw new InvalidActionException("The strongbox doesn't contain some resources you want to remove!");
            for(Resource resource:resources) {
                removeResource(resource);
                numberResources--;
            }
        }
    }

    private void removeResource(Resource resource){
        TypeResource type = resource.getType();
        for(int i=0;i<content.size();i++){
            if(content.get(i).getType().equals(type)){
                content.remove(i);
                break;
            }
        }
    }

    /**
     * Counts from "resources" the resource you want to count
     * @param resources
     * @param resource
     * @return
     */
    private int countResource(ArrayList<Resource> resources, Resource resource){
        int count = (int) resources.stream().filter(r -> r.getType().equals(resource.getType())).count();
        return count;
    }

    /**
     * Verifies if the content contains all "resources" in type and number
     * @param resources
     * @return
     */

    private boolean checkEnoughResources(ArrayList<Resource> resources){
        ArrayList<Resource> typeResources = new ArrayList<>();
        typeResources.add(new Resource(Color.YELLOW));
        typeResources.add(new Resource(Color.BLUE));
        typeResources.add(new Resource(Color.PURPLE));
        typeResources.add(new Resource(Color.GREY));
        for(Resource resource: typeResources){
            if(countResource(content,resource)<countResource(resources,resource)) return false;
        }
        return true;
    }
}