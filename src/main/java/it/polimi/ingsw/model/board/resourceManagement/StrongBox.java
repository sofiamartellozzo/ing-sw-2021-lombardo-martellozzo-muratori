package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * It represents the StrongBox in the PersonalBoard, it can contain infinite resources.
 * The "numberResources" attribute indicates how many resources are in.
 * The "content" attribute is an ArrayList<Resource> contained by the strongbox.
 * */

public class StrongBox{
    private ArrayList<Resource> content;

    /**
     * Constructor
     * Initialized the content.
     */
    public StrongBox() {
        this.content = new ArrayList<>();
    }

    /**
     * @return -> The number of resources contained by the strongbox
     */
    public int getNumberResources() {
        return content.size();
    }

    /**
     * Getter Method
     * @return -> The content of the strongbox as an ArrayList<Resource>
     */
    public ArrayList<Resource> getContent() {
        return content;
    }

    /**
     * Add all "resources" in the strongbox.
     * After checking:
     * - "Resources" can't be null or empty.
     * @param resources -> The resources you want to put in
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void addResources(ArrayList<Resource> resources) throws InvalidActionException {
        if(resources==null || resources.isEmpty()) throw new InvalidActionException("No resources to add!");
        content.addAll(resources);
    }

    /**
     * Remove all "resources" from the strongbox.
     * After checking:
     * - "resources" is not empty or null;
     * - "content" is not empty;
     * - The strongbox contains all resources to remove
     * @param resources -> The resources you want to remove
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResources(ArrayList<Resource> resources) throws InvalidActionException {
        if(resources!=null){
            if(content.isEmpty() && !resources.isEmpty()) throw new InvalidActionException("The strongbox is empty");
            if(!checkEnoughResources(resources)) throw new InvalidActionException("The strongbox doesn't contain some resources you want to remove!");
            for(Resource resource:resources) {
                removeResource(resource);
            }
        }
    }

    /**
     * PRIVATE METHOD
     * Remove a single resource.
     * @param resource -> The resource to remove
     * NOTE: Supports the "removeResources" method.
     */
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
     * Counts from "resources" the resource you want to count.
     * @param content -> The content where to count
     * @param resource -> The resource to count
     * @return -> How many resources of type "resource" are in "content"
     */
    public int countResource(ArrayList<Resource> content, Resource resource){
        int count = (int) content.stream().filter(r -> r.getType().equals(resource.getType())).count();
        return count;
    }

    /**
     * Checks if all "resources" are contained by the strongbox
     * @param resources
     * @return
     */

    public boolean checkEnoughResources(ArrayList<Resource> resources){
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

    /**
     * @return -> A photographic situation of the strongbox content as an ArrayList<TypeResource>
     */
    public ArrayList<TypeResource> getInstanceStrongbox(){
        ArrayList<TypeResource> instance = new ArrayList<>();
        for(Resource resource: content){
            instance.add(resource.getType());
        }
        return instance;
    }
}