package it.polimi.ingsw.Model.board.resourceManagement;

/*
 * GIANLUCA
 * */

import com.sun.jdi.InvalidLineNumberException;
import it.polimi.ingsw.Exception.*;
import it.polimi.ingsw.Model.Resource;

public class WarehouseStandard extends Warehouse{

    /**
     * It recalls the method of depot.putResource(resource), if depot and resource aren't null,
     * else throws exception. The depot must be contained in the ArrayList<Depot>.
     * @param resource
     * @param depot
     */
    @Override
    public void addResource(Resource resource, Depot depot) throws InvalidActionException {
        if(depot==null) throw new InvalidActionException("Choose a depot!");
        if(resource==null)throw new InvalidActionException("Choose a resource");
        if(!depots.contains(depot))throw new InvalidActionException("The depot doesn't exist!");
        depot.putResource(resource);
    }

    /**
     * It move a Resource from depot1 (removeResource) to depot2(putResource).
     * resource, depot1, depot2 can't be null.
     * resource must be contained in the depot1.
     * depot1 and depot2 must be contained in the ArrayList<depot>. RIVEDERE
     * depot2 must have the space to contain the moved resource.
     *
     * @param resource
     * @param depot1
     * @param depot2
     */
    @Override
    public void moveResource(Resource resource, Depot depot1, Depot depot2) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Choose a resource!");
        if(depot1==null) throw new InvalidActionException("Choose the first depot!");
        if(depot2==null) throw new InvalidActionException("Choose the second depot!");
        if(!depot1.getResources().get(0).equals(resource))throw new InvalidActionException("The resource you want to move doesn't exist!");
        if(!depots.contains(depot1) || !depots.contains(depot2))throw new InvalidActionException("The depots or one of them don't exist!");
        depot1.removeResource();
        depot2.putResource(resource);
    }

    /**
     * It recalls the method of depot.removeResource(resource). if depot and resource aren't null,
     * else throws exception. The depot must be contained in the ArrayList<Depot>.
     * @param resource
     * @param depot
     */
    @Override
    public void removeResource(Resource resource, Depot depot) throws InvalidActionException {
        if(depot==null) throw new InvalidActionException("Choose a depot!");
        if(resource==null) throw new InvalidActionException("Choose a resource!");
        if(!depots.contains(depot)) throw new InvalidActionException("The resource you want to remove doesn't exist!");
        depot.removeResource();
    }
}
