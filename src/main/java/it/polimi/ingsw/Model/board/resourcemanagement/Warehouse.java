package it.polimi.ingsw.Model.board.resourcemanagement;

import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/*
* GIANLUCA
* La mia idea era di aggiungere un attributo ArrayList<Depot>
* per avere un riferimento a quale deposito (inserito dal giocatore).
* In questo modo chiamando i metodi, avendo un riferimento al deposito in cui
* aggiungere o rimuovere la risorsa, chiamo in sequenza il metodo
* depositodiriferimento.addresource/removeresource.
* Volevo costruirlo qui perché sia la WarehouseStandard sia il Decorator devono
* avere entrambi un riferimento al deposito
* */

public class Warehouse implements ResourceManagement{
    private ArrayList<Depot> depots;

    /*
    * It recalls the method of depot.putResource(resource), if depot and resource aren't null,
    * else throws exception. The depot must be contained in the ArrayList<Depot>,.
    */
    @Override
    public void addResource(Resource resource, Depot depot) {
        if(depot==null || resource==null){
            //throws exception;
        }
        if(!depots.contains(depot)){
            //throws exception;
        }
        depot.putResource();
    }

    /*
     * It recalls the method of depot.removeResource(resource). if depot and resource aren't null,
     * else throws exception. The depot must be contained in the ArrayList<Depot>.
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

    /*
    * It move a Resource from depot1 (removeResource) to depot2(putResource).
    * resource, depot1, depot2 can't be null.
    * resource must be contained in the depot1. RIVEDERE
    * depot1 and depot2 must be contained in the ArrayList<depot>. RIVEDERE
    * depot2 must have the space to contain the moved resource.
    * */
    public void moveResource(Resource resource, Depot depot1, Depot depot2){
        if(resource==null || depot1==null || depot2==null){
            //throws exception
        }
        if(/*deposito contiene la risorsa che vogliamo spostare*/){
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

}
