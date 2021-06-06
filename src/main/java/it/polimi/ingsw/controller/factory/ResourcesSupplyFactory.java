package it.polimi.ingsw.controller.factory;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ResourcesSupply;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * FACTORY
 * this class creates the Resources Supply, one for all the player in a Room
 * it is composed of 4 cell, each one of them contains a different type
 * of resource.
 * We suppose that the number of resources in each cell are unlimited
 */
public class ResourcesSupplyFactory {
    public ResourcesSupply createTheResourcesSupply(){

        Cell cell1 = new Cell(TypeResource.COIN);
        Cell cell2 = new Cell(TypeResource.SERVANT);
        Cell cell3 = new Cell(TypeResource.STONE);
        Cell cell4 = new Cell(TypeResource.SHIELD);
        ArrayList<Cell> list = new ArrayList<>();
        list.add(cell1);
        list.add(cell2);
        list.add(cell3);
        list.add(cell4);
        return new ResourcesSupply(list);
    }
}
