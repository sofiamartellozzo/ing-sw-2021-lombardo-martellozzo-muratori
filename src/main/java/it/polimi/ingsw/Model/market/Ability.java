package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Resource;

/**
 * GIANLUCA
 * Maybe we can eliminate it...
 * */
public class Ability {
    private boolean active;
    private Resource resource;

    /**
     * Constructor
     * @param resource
     */
    public Ability(Resource resource) {
        this.active=false;
        this.resource = resource;
    }

    public boolean isActive() {
        return active;
    }

    public Resource getResource() {
        return resource;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setResource(Resource resource){
        this.resource=resource;
    }

    public Resource choose(){
        if(this.isActive()==true) return new Resource(resource.getColor());
        else {
            return null;
        }
    }
}
