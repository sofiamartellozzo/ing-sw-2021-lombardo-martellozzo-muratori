package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the Warehouse.
 * The "depots" attribute contains the depots which compose the warehouse.
 */

public abstract class Warehouse implements Serializable {
    protected ArrayList<Depot> depots;

    /**
     * Constructor
     * Initialize "depots", adding three RealDepot of increasing size and floor
     */
    public Warehouse() {
        depots = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            depots.add(new RealDepot(i + 1, i + 1));
        }
    }

    /**
     * Add "resource" in "depot".
     * After checking:
     * - "depot" must be one of those contained by the warehouse
     * - exists a depot where the resource can be put
     * @param resource -> The resource you want to add
     * @param depot -> The depot where to add the resource
     * @throws InvalidActionException -> If one of the conditions is not respected.
     */
    public void addResource(Resource resource, int depot) throws InvalidActionException {
        if ((depot < 1) || (depot > depots.size())) throw new InvalidActionException("Choose a depot!");
        Depot depot1=depots.get(depot-1);
        if(!checkAvailableDepot(resource)) throw new InvalidActionException("No depot can contain that resource!");
        ArrayList<Integer> availableDepot = getAvailableDepot(resource);
        boolean available=false;
        for(Integer i:availableDepot){
            if(i==depot){
                available=true;
            }
        }
        if(!available) throw new InvalidActionException("The resource can't be add here");
        depot1.addResource(resource);
    }

    /**
     * Remove a resource from "depot".
     * After checking:
     * - "depot" must be one of those contained by the warehouse.
     * @param depot -> The depot where to remove the resource
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResource(int depot) throws InvalidActionException {
        if (depot <= 0 || depot > depots.size()) throw new InvalidActionException("Choose a depot!");
        depots.get(depot-1).removeResource();
    }

    /**
     * Move or exchange the content from "fromDepot" to "toDepot".
     * The exchange can be possible only if both the depots are RealDepot.
     * After checking:
     * - The "fromDepot" is not empty;
     * - If one of the depot is an AbilityDepot, both the depots content need to be of the same type;
     * - If one of the depot is an AbilityDepot, the "toDepot" can't be full;
     * - In case of exchange, both the depots must be able to contain the resources of the other
     * - In case of both the depots are RealDepot and the "toDepot" is empty, the "toDepot" must be able to contain all resources of "fromDepot"
     * @param fromDepot -> The depot where move from
     * @param toDepot -> The depot where move to
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void moveResource(int fromDepot,int toDepot) throws InvalidActionException {
        if(fromDepot<1 || fromDepot>depots.size()) throw new InvalidActionException("First depot not valid!");
        if(toDepot<1 || toDepot>depots.size()) throw new InvalidActionException("Second depot not valid!");
        if(fromDepot==toDepot) throw new InvalidActionException("You're moving in the same depot!");
        Depot from = depots.get(fromDepot-1);
        Depot to = depots.get(toDepot-1);
        if(!from.isEmpty()){
            if(from instanceof AbilityDepot || to instanceof AbilityDepot){ //At least one of the depot is an AbilityDepot
                if(to.getType()!=null && from.getType().equals(to.getType())){ //The "to" depot type is not null (in case is a RealDepot) and has the same type of the "from" depot
                    if(!to.isFull()){ //The "to" depot must not be full
                        to.addResource(new Resource(from.getType()));
                        from.removeResource();
                    }else{
                        throw new InvalidActionException("The depot you're trying to move is full!");
                    }
                }else if (to.getType()==null){ //The "to" depot is a RealDepot and is empty
                    to.addResource(new Resource(from.getType()));
                    from.removeResource();
                }else if(!from.getType().equals(to.getType())){
                    throw new InvalidActionException("The content type isn't the same!");
                }
            }else{ //both the depots are RealDepot
                //REMEMBER: two RealDepot has always DIFFERENT content type
                if(to.isEmpty() && from.getNumberResources()<=to.getSize()){ //The "to" depot is empty and can contains all resources of the "from" depot
                    for(Resource resource: from.getResources()){
                        to.addResource(resource);
                    }
                    from.removeResources(from.getNumberResources());
                }else if(!to.isEmpty()){ //The "to" is not empty, the player can exchange the two contents if the size allows to
                    if(to.getNumberResources()<=from.getSize() || from.getNumberResources()<=to.getSize()){
                        //Clone the two contents
                        ArrayList<Resource> contentFrom = (ArrayList<Resource>) from.getResources().clone();
                        ArrayList<Resource> contentTo = (ArrayList<Resource>) to.getResources().clone();
                        //Clear
                        from.removeResources(from.getNumberResources());
                        to.removeResources(to.getNumberResources());
                        //Invert the contents
                        from.addResources(contentTo.size(),contentTo.get(0));
                        to.addResources(contentFrom.size(),contentFrom.get(0));
                    }else{
                        throw new InvalidActionException("You can't exchange the contents");
                    }
                }else{
                    throw new InvalidActionException("There's no enough space in the depot you want to move to");
                }
            }
        }else{
            throw new InvalidActionException("There's no resource to be moved!");
        }
    }

    /**
     * Getter Method
     * @return -> The "depots" attribute as an ArrayList<Depot>
     */
    public ArrayList<Depot> getDepots() {
        return depots;
    }

    /**
     * Check if a resource can be put in at least one depot.
     * @param resource -> The resource you would put.
     * @return -> True if the resource can be put, else false.
     */
    public boolean checkAvailableDepot(Resource resource) throws InvalidActionException {
       ArrayList<Integer> availableDepot = getAvailableDepot(resource);
       if(availableDepot==null || availableDepot.size()==0){
           return false;
       }else{
           return true;
       }
    }

    /**
     * @return -> The content of the warehouse as an ArrayList<Resource>
     */
    public ArrayList<Resource> getContent(){
        ArrayList<Resource> content = new ArrayList<>();
        for(Depot depot:depots){
            content.addAll(depot.getResources());
        }
        return content;
    }

    /**
     * Remove all "resources" from the warehouse.
     * After checking:
     * - "resources" is not null and not empty
     * - All "resources" must be contained by the warehouse.
     * @param resources -> The resources you want to remove
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public void removeResources(ArrayList<Resource> resources) throws InvalidActionException {
        if(resources==null || resources.isEmpty()) throw new InvalidActionException("There's no resource to remove");
        if(!checkEnoughResources(resources)) throw new InvalidActionException("The Warehouse doesn't contain some resources you want to remove!");
        for(Resource resource:resources){
            depots.get(searchResource(resource)-1).removeResource();
        }
    }

    /**
     * Count how many resources of type "resource" there are in the "content".
     * @param content -> The content where to count
     * @param resource -> The resource to count
     * @return -> The number of resources
     */
    public int countResource(ArrayList<Resource> content, Resource resource){
        int count = (int) content.stream().filter(r -> r.getType().equals(resource.getType())).count();
        return count;
    }

    /**
     * Checks if all "resources" are contained in the warehouse
     * @param resources -> The resources to check
     * @return -> True if all "resources" are contained, else false.
     */
    public boolean checkEnoughResources(ArrayList<Resource> resources){
        ArrayList<Resource> typeResources = new ArrayList<>();
        typeResources.add(new Resource(Color.YELLOW));
        typeResources.add(new Resource(Color.BLUE));
        typeResources.add(new Resource(Color.PURPLE));
        typeResources.add(new Resource(Color.GREY));
        for(Resource resource: typeResources){
            if(countResource(getContent(),resource)<countResource(resources,resource)) return false;
        }
        return true;
    }

    /**
     * Search the "resource" in the warehouse.
     * @param resource -> The resource to search
     * @return -> The depot floor where the resource was found, else -1
     */
    public int searchResource(Resource resource){
        for(int i=0; i< depots.size(); i++){
            if(depots.get(i).getType()!=null && depots.get(i).getType().equals(resource.getType())){
                return i+1;
            }
        }
        return -1;
    }

    /**
     * @return -> A photographic situation of the warehouse
     */
    public HashMap<Integer,ArrayList<TypeResource>> getInstanceContent(){
        HashMap<Integer,ArrayList<TypeResource>> content = new HashMap<Integer,ArrayList<TypeResource>>();
        for(Depot depot:depots){
            TypeResource type = depot.getType();
            ArrayList<TypeResource> resources = new ArrayList<>();
            for (int i = 0; i < depot.getResources().size(); i++) {
                resources.add(type);
            }
            content.put(depot.getFloor(),resources);
        }
        return content;
    }

    /**
     * After checking:
     * - The "resource" is not null
     * @param resource -> The resource you want to put in the warehouse
     * @return -> An ArrayList<Integer> which contains which floor of the depot are available to put the "reosurce"
     * @throws InvalidActionException -> If one of the conditions is not respected
     */
    public ArrayList<Integer> getAvailableDepot(Resource resource) throws InvalidActionException {
        ArrayList<Integer> whichDepot = new ArrayList<>();
        if(resource==null) throw new InvalidActionException("Resource not valid");
        for(int i=0;i<depots.size();i++){
            boolean available=false;
            //Get the depot to check
            Depot depot1=depots.get(i);
            //If it's one of the first three depots
            //Check if it's empty or contains the same type of resource and has space
            if((depot1 instanceof RealDepot) && (depot1.isEmpty() || (depot1.getType().equals(resource.getType()) && !depot1.isFull()) )){
                //For now it's available
                available=true;
                //But checking if other "normal" depots already contains that type
                for(int j=0;j<depots.size();j++){
                    //If not the same depot
                    if(i!=j){
                        //Getting the other depot
                        Depot depot2=depots.get(j);
                        //If it's one of the first three depot and contains the same type
                        if(depot2 instanceof RealDepot && !depot2.isEmpty() && depot2.getType().equals(resource.getType())){
                            //So the depot found as available it's no more available
                            available=false;
                            break;
                        }
                    }
                }
                //If it's one the special depot, just check the content type and if it's not full
            }else if(depot1 instanceof AbilityDepot && depot1.getType().equals(resource.getType()) && !depot1.isFull()){
                available=true;
            }
            //If available add to the possible choices
            if(available){
                whichDepot.add(depot1.getFloor());
            }
        }
        return whichDepot;
    }

}