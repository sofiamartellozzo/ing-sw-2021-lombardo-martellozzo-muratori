package it.polimi.ingsw.Model.board.resourcemanagement;

import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

public class StrongBox implements ResourceManagement{
    private int numberResources;
    private ArrayList<Resource> content;

    public StrongBox(int numberResources, Resource[] content) {
        this.numberResources = 0;
        this.content = new ArrayList<>();
    }

    public int getNumberResources() {
        return numberResources;
    }

    public ArrayList<Resource> getContent() {
        return content;
    }

    public void setNumberResources(int numberResources) {
        this.numberResources = numberResources;
    }

    public void setContent(ArrayList<Resource> content) {
        this.content = content;
    }

    @Override
    public void addResources(Resource resource) {
        this.content.add(resource);
    }

    @Override
    public void removeResources(Resource resource) {

    }
}
