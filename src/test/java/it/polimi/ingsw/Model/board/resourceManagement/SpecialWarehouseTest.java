package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpecialWarehouseTest {
    SpecialWarehouse specialWarehouse;
    Warehouse warehouse;

    @Before
    public void setUp() throws Exception {
        warehouse = new WarehouseStandard();
        specialWarehouse=new SpecialWarehouse(warehouse,new Resource(Color.YELLOW));
    }

    @After
    public void tearDown() throws Exception {
        warehouse = null;
        specialWarehouse=null;
    }

    @Test
    public void createDepotAndPutResourcesIn() throws InvalidActionException {
        assertEquals((new Resource(Color.YELLOW)).getType(),warehouse.getDepots().get(3).getType());
        assertSame(4,warehouse.getDepots().size());
        Resource coin;
        Resource shield;
        Resource stone;
        Resource servant;
        ArrayList<Resource> expectedDepot1 = new ArrayList<>();
        ArrayList<Resource> expectedDepot2 = new ArrayList<>();
        ArrayList<Resource> expectedDepot3 = new ArrayList<>();
        ArrayList<Resource> expectedDepot4 = new ArrayList<>();
        coin = new Resource(Color.YELLOW);
        warehouse.addResource(coin,1);
        expectedDepot1.add(coin);
        shield = new Resource(Color.BLUE);
        warehouse.addResource(shield, 2);
        expectedDepot2.add(shield);
        servant = new Resource(Color.PURPLE);
        warehouse.addResource(servant,3);
        expectedDepot3.add(servant);
        coin= new Resource(Color.YELLOW);
        warehouse.addResource(coin,4);
        expectedDepot4.add(coin);
        coin= new Resource(Color.YELLOW);
        warehouse.addResource(coin,4);
        expectedDepot4.add(coin);
        servant = new Resource(Color.PURPLE);
        warehouse.addResource(servant,3);
        expectedDepot3.add(servant);
        specialWarehouse = new SpecialWarehouse(warehouse,new Resource(Color.PURPLE));
        ArrayList<Resource> expectedDepot5 = new ArrayList<>();
        servant = new Resource(Color.PURPLE);
        warehouse.addResource(servant,5);
        expectedDepot5.add(servant);
        servant = new Resource(Color.PURPLE);
        warehouse.addResource(servant,3);
        expectedDepot3.add(servant);
        assertEquals(expectedDepot1,warehouse.getDepots().get(0).getResources());
        assertEquals(expectedDepot2,warehouse.getDepots().get(1).getResources());
        assertEquals(expectedDepot3,warehouse.getDepots().get(2).getResources());
        assertEquals(expectedDepot4,warehouse.getDepots().get(3).getResources());
        assertEquals(expectedDepot5,warehouse.getDepots().get(4).getResources());
    }
}