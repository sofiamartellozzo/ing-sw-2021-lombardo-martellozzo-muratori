package it.polimi.ingsw.Model.board.resourceManagement;

/*
 * GIANLUCA
 * */

import it.polimi.ingsw.Model.Resource;

public class WarehouseStandard extends Warehouse{

    /**
     * It recalls the method of depot.putResource(resource), if depot and resource aren't null,
     * else throws exception. The depot must be contained in the ArrayList<Depot>.
     * @param resource
     * @param depot
     */
    @Override
    public void addResource(Resource resource, Depot depot) {
        if(depot==null || resource==null){
            //throws exception;
        }
        if(!depots.contains(depot)){
            //throws exception;
        }
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
    public void moveResource(Resource resource, Depot depot1, Depot depot2) {
        if(resource==null || depot1==null || depot2==null){
            //throws exception
        }
        if(depot1.getResources().get(0).equals(resource)){
            //throws exception
        }
        if(!depots.contains(depot1) || !depots.contains(depot2)){
            //però teoricamente non è detto che sia lo stesso arraylist uno potrebbe essere
            //deposito speciale e uno no
            //throws exception
        }
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
    public void removeResource(Resource resource, Depot depot) {
        if(depot==null || resource==null){
            //throws exception;
        }
        if(!depots.contains(depot)){
            //throws exception;
        }
        depot.removeResource();
    }
}
