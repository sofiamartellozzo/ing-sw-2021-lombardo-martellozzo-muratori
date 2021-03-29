package it.polimi.ingsw.Model.board.resourcemanagement;

import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/*
* GIANLUCA
* It represents the StrongBox in the PersonalBoard, it can contains
* infinite resources.
* Attributes:
* numberResources -> it's the number of resources that the StrongBox contains
* in that moment;
* content -> the content of the StrongBox.
* */

public class StrongBox implements ResourceManagement{
    private int numberResources;
    private ArrayList<Resource> content;

    /*
    * Constructor
    * */

    public StrongBox() {
        this.numberResources = 0;
        this.content = new ArrayList<>();
    }

    /*
    * Getter methods*/

    public int getNumberResources() {
        return numberResources;
    }

    public ArrayList<Resource> getContent() {
        return content;
    }

    /*
    * It adds a resource in the strongbox, increasing the
    * "numberResources" attribute by one.
    * */

    @Override
    public void addResource(Resource resource) {
        content.add(resource);
        numberResources++;
    }

    /*
     * It removes a resource in the strongbox, decreasing the
     * "numberResources" attribute by one, after checking if
     * it is not empty, else throws exception.
     * */
    @Override
    public void removeResource(Resource resource) {
        if(content.isEmpty()){
            //throws Exception
        }
        content.remove(resource);
        numberResources--;
    }
}
