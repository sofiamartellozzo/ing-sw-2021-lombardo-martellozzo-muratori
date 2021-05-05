package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ResourcesSupplyTest extends TestCase {

    ResourcesSupply resourcesSupply = null;

    @Before
    public void setUp() throws Exception {

       Cell cell1 = new Cell(TypeResource.COIN);
       Cell cell2 = new Cell(TypeResource.SERVANT);
       Cell cell3 = new Cell(TypeResource.SHIELD);
       Cell cell4 = new Cell(TypeResource.STONE);

       ArrayList<Cell> cells = new ArrayList<>();
       cells.add(cell1);
       cells.add(cell2);
       cells.add(cell3);
       cells.add(cell4);

        resourcesSupply = new ResourcesSupply(cells);
    }

    @After
    public void tearDown() throws Exception {
        resourcesSupply = null;
    }

    @Test
    public void testGetContent() {
        Cell cell1 = new Cell(TypeResource.COIN);
        Cell cell2 = new Cell(TypeResource.SERVANT);
        Cell cell3 = new Cell(TypeResource.SHIELD);
        Cell cell4 = new Cell(TypeResource.STONE);

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        cells.add(cell4);

        resourcesSupply.getContent(cell1);
        assertEquals(resourcesSupply.getContent(cell1).get(1).getType(),TypeResource.SERVANT);

    }

     @Test
    public void testReturnResourceAsked() throws IllegalAccessException {

        Resource res = new Resource(Color.BLUE);
        assertEquals(resourcesSupply.returnResourceAsked(res).getType(),TypeResource.SHIELD);
    }
}