package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResourceManagerTest {
    ResourceManager resourceManager;
    Warehouse warehouse;
    StrongBox strongBox;
    @Before
    public void setUp() throws Exception {
        warehouse=new WarehouseStandard();
        strongBox=new StrongBox();
        resourceManager=new ResourceManager(strongBox,warehouse);
    }

    @After
    public void tearDown() throws Exception {
        warehouse=null;
        strongBox=null;
        resourceManager=null;
    }

    @Test
    public void getStrongBox() {
        assertEquals(strongBox,resourceManager.getStrongBox());
    }

    @Test
    public void getWarehouse() {
        assertEquals(warehouse,resourceManager.getWarehouse());
    }

    @Test
    public void numberAllResources() throws InvalidActionException {
        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        Resource stone = new Resource(Color.GREY);
        Resource servant = new Resource(Color.PURPLE);

        ArrayList<Resource> resources = new ArrayList<>();
        int num=0;

        for(int i=0;i<8;i++){
            resources.add(coin);
            num++;
            resources.add(stone);
            num++;
        }
        resourceManager.addResourcesToStrongBox(resources);
        resourceManager.addResourceToWarehouse(servant,2);
        num++;
        resourceManager.addResourceToWarehouse(servant,2);
        num++;
        resourceManager.addResourceToWarehouse(shield,1);
        num++;
        SpecialWarehouse specialWarehouse = new SpecialWarehouse(resourceManager.getWarehouse(), shield);
        resourceManager.addResourceToWarehouse(shield,4);
        num++;
        assertSame(num,resourceManager.numberAllResources());
    }

    @Test
    public void getResources() throws InvalidActionException {
        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        Resource stone = new Resource(Color.GREY);
        Resource servant = new Resource(Color.PURPLE);

        ArrayList<Resource> resources = new ArrayList<>();

        for(int i=0;i<8;i++){
            resources.add(coin);
            resources.add(stone);
        }
        resourceManager.addResourcesToStrongBox(resources);
        resourceManager.addResourceToWarehouse(servant,2);
        resourceManager.addResourceToWarehouse(servant,2);
        resourceManager.addResourceToWarehouse(shield,1);
        SpecialWarehouse specialWarehouse = new SpecialWarehouse(resourceManager.getWarehouse(), shield);
        resourceManager.addResourceToWarehouse(shield,4);

        ArrayList<Resource> expected = new ArrayList<>();
        expected.add(shield);
        expected.add(servant);
        expected.add(servant);
        expected.add(shield);
        for(int i=0;i<8;i++){
            expected.add(coin);
            expected.add(stone);
        }
        assertEquals(expected,resourceManager.getResources());
    }

    @Test
    public void removeResources() throws InvalidActionException {
        // Depot 1 -> 1 Shield
        // Depot 2 -> 2 Servants
        // Depot 3 -> EMPTY
        // Depot 4 -> 1 Shield
        // StrongBox -> 8 Coins, 8 Stones
        this.getResources();

        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        Resource stone = new Resource(Color.GREY);
        Resource servant = new Resource(Color.PURPLE);

        ArrayList<Resource> resources = new ArrayList<>();

        resources.add(servant);
        resources.add(coin);
        resources.add(stone);
        resources.add(shield);
        resources.add(stone);
        resources.add(coin);
        resources.add(coin);
        resources.add(shield);

        resourceManager.removeResources(resources);

        ArrayList<Resource> expected = new ArrayList<>();

        expected.add(servant);
        expected.add(stone);
        expected.add(coin);
        expected.add(stone);
        expected.add(coin);
        expected.add(stone);
        expected.add(coin);
        expected.add(stone);
        expected.add(coin);
        expected.add(stone);
        expected.add(coin);
        expected.add(stone);

        assertSame(expected.size(),resourceManager.getResources().size());
        for(int i=0;i<expected.size();i++){
            assertEquals(expected.get(i).getType(),resourceManager.getResources().get(i).getType());
        }
    }

    @Test(expected = InvalidActionException.class)
    public void removeResources_NotEnoughResources_InvalidActionException() throws InvalidActionException {
        // Depot 1 -> 1 Shield
        // Depot 2 -> 2 Servants
        // Depot 3 -> EMPTY
        // Depot 4 -> 1 Shield
        // StrongBox -> 8 Coins, 8 Stones
        this.getResources();

        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        Resource stone = new Resource(Color.GREY);
        Resource servant = new Resource(Color.PURPLE);

        ArrayList<Resource> resources = new ArrayList<>();

        resources.add(shield);
        resources.add(shield);
        resources.add(shield);

        resourceManager.removeResources(resources);
    }


    @Test
    public void getVictoryPoints() throws InvalidActionException {
        // Depot 1 -> 1 Shield
        // Depot 2 -> 2 Servants
        // Depot 3 -> EMPTY
        // Depot 4 -> 1 Shield
        // StrongBox -> 8 Coins, 8 Stones
        this.getResources();

        int victoryPoints = (1+2+0+1+8+8)/5;
        assertSame(victoryPoints,resourceManager.getVictoryPoints());
    }

    //Methods "FromWarehouse/StrongBox" and "ToWarehouse/StrongBox"
    //not tested because they've been tested in the appropriate tests.

}