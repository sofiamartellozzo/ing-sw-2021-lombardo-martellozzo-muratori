package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/*
* GIANLUCA
* It represents one of the three depots from the warehouse.
* Attributes:
* resource -> indicates what and how many resources are in the depot;
* floor -> indicates which of the three floors;
* size -> indicates how many resources we can add in the depot.
* */

public class RealDepot implements Depot{
    private ArrayList<Resource> resources;
    private final int floor;
    private final int size;

    /*
    * Constructor
    * Just set the floor and the size
    * */

    public RealDepot(int floor, int size) {
        this.resources= new ArrayList<>();
        this.floor = floor;
        this.size=size;
    }

    /*
    * Getter methods
    * */
    public ArrayList<Resource> getResources() {
        return resources;
    }

    public int getFloor() {
        return floor;
    }

    public int getSize() {
        return size;
    }

    /*
    * It adds at the end in the ArrayList one resource,
    * after checking if the depot isn't full
    * and checking if the resource we want to put
    * into is the same type of the resources already
    * contained in the ArrayList, if there are.
    * If one of this condition isn't satisfied
    * the method throws the relative Exception.
    * */
    @Override
    public void putResource(Resource resource) {
        if(resources.size()>=getSize()){
            //throws Exception;
        }
        if(!resources.isEmpty() && !resources.contains(resource)){
            //throws Exception;
        }
        resources.add(resource);
    }

    /*
    * It removes at the end in the ArrayList one resource,
    * after checking if the depot isn't empty, else throws Exception
    * */
    @Override
    public void removeResource() {
        if(resources.isEmpty()){
            //throws Exception
        }
        resources.remove(resources.size());
    }
}
