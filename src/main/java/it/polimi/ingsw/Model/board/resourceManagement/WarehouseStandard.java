package it.polimi.ingsw.Model.board.resourceManagement;

/*
 * GIANLUCA
 * */

import com.sun.jdi.InvalidLineNumberException;
import it.polimi.ingsw.Exception.*;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

public abstract class WarehouseStandard extends Warehouse implements Depot {

    /**
     * It recalls the method of depot.putResource(resource), if depot and resource aren't null,
     * else throws exception. The depot must be contained in the ArrayList<Depot>.
     * @param resource
     * @param depot
     */
    @Override
    public void addResource(Resource resource, int depot) throws InvalidActionException {
        if(depot<=0 || depot>getDepots().size()) throw new InvalidActionException("Choose a depot!");
        if(resource==null)throw new InvalidActionException("Choose a resource");
        if(!depots.contains(getDepots().get(depot-1)))throw new InvalidActionException("The depot doesn't exist!");
        getDepots().get(depot-1).putResource(resource);
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
    public void moveResource(Resource resource, int depot1, int depot2) throws InvalidActionException {
        if(resource==null) throw new InvalidActionException("Choose a resource!");
        if(depot1<=0 || depot1>getDepots().size()) throw new InvalidActionException("Choose the first depot!");
        if(depot2<=0 || depot2>getDepots().size()) throw new InvalidActionException("Choose the second depot!");
        if(!getDepots().get(depot1-1).getResources().get(0).equals(resource))throw new InvalidActionException("The resource you want to move doesn't exist!");
        if(!depots.contains(getDepots().get(depot1-1)) || !depots.contains(getDepots().get(depot2-1))) throw new InvalidActionException("The depots or one of them don't exist!");
        getDepots().get(depot1-1).removeResource();
        getDepots().get(depot2-1).putResource(resource);
    }

    /**
     * It recalls the method of depot.removeResource(resource). if depot and resource aren't null,
     * else throws exception. The depot must be contained in the ArrayList<Depot>.
     * @param resource
     * @param depot
     */
    @Override
    public void removeResource(Resource resource, int depot) throws InvalidActionException {
        if(depot<=0 || depot>getDepots().size()) throw new InvalidActionException("Choose a depot!");
        if(resource==null) throw new InvalidActionException("Choose a resource!");
        if(!depots.contains(getDepots().get(depot-1))) throw new InvalidActionException("The resource you want to remove doesn't exist!");
        getDepots().get(depot).removeResource();
    }

    @Override
    public ArrayList<Depot> getDepots() {
        return super.getDepots();
    }
}
