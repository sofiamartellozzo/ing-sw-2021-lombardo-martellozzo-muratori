package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

public class ResourcesSupplyFactory {
    public ResourcesSupply createTheResourcesSupply(){
        //before I have to create an array of resources, and his cell
        //this 4 times and then put all in the constructor of the Resources Supply
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
