package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class WarehouseTest {
    Warehouse warehouse;
    ArrayList<Resource> depot1 = new ArrayList<>();
    ArrayList<Resource> depot2 = new ArrayList<>();
    ArrayList<Resource> depot3 = new ArrayList<>();
    ArrayList<Resource> depot4 = new ArrayList<>();
    ArrayList<Resource> depot5 = new ArrayList<>();
    Resource coin = new Resource(Color.YELLOW);
    Resource shield = new Resource(Color.BLUE);
    Resource servant = new Resource(Color.PURPLE);
    Resource stone = new Resource(Color.GREY);

    @Before
    public void setUp() throws Exception {
        warehouse=new WarehouseStandard();
    }

    @After
    public void tearDown() throws Exception {
        warehouse=null;
    }

    @Test
    public void addResource() throws InvalidActionException {
        assertSame(3,warehouse.getDepots().size());
        warehouse.addResource(shield,1);
        depot1.add(shield);
        warehouse.addResource(coin,2);
        depot2.add(coin);
        warehouse.addResource(stone,3);
        depot3.add(stone);
        warehouse.addResource(stone,3);
        depot3.add(stone);
        assertSame(3,warehouse.getDepots().size());
        assertSame(depot1.size(),warehouse.getDepots().get(0).getResources().size());
        assertEquals(depot1,warehouse.getDepots().get(0).getResources());
        assertSame(depot2.size(),warehouse.getDepots().get(1).getResources().size());
        assertEquals(depot2,warehouse.getDepots().get(1).getResources());
        assertSame(depot3.size(),warehouse.getDepots().get(2).getResources().size());
        assertEquals(depot3,warehouse.getDepots().get(2).getResources());
        warehouse.removeResource(2);
        depot2.remove(coin);
        assertSame(depot2.size(),warehouse.getDepots().get(1).getResources().size());
        assertEquals(depot2,warehouse.getDepots().get(1).getResources());
        warehouse.addResource(servant,2);
        warehouse.addResource(servant,2);
        depot2.add(servant);
        depot2.add(servant);
        assertSame(depot2.size(),warehouse.getDepots().get(1).getResources().size());
        assertEquals(depot2,warehouse.getDepots().get(1).getResources());
        SpecialWarehouse specialWarehouse = new SpecialWarehouse(warehouse,shield);
        assertSame(4,warehouse.getDepots().size());
        warehouse.addResource(shield,4);
        depot4.add(shield);
        assertSame(depot4.size(),warehouse.getDepots().get(3).getResources().size());
        assertEquals(depot4,warehouse.getDepots().get(3).getResources());
        warehouse.addResource(shield,4);
        depot4.add(shield);
        assertSame(depot4.size(),warehouse.getDepots().get(3).getResources().size());
        assertEquals(depot4,warehouse.getDepots().get(3).getResources());
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
    }

    @Test (expected = InvalidActionException.class)
    public void addResource_WrongDepot_InvalidActionException() throws InvalidActionException {
        warehouse.addResource(shield,0);
    }

    @Test (expected = InvalidActionException.class)
    public void addResource_NoAvailableDepot_InvalidActionException() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        addResource();
        warehouse.addResource(coin,1);
    }

    @Test
    public void removeResource() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        this.addResource();
        warehouse.removeResource(4);
        depot4.remove(shield);
        warehouse.removeResource(3);
        warehouse.removeResource(3);
        depot3.remove(stone);
        depot3.remove(stone);
        warehouse.removeResource(2);
        depot2.remove(servant);
        assertSame(4,warehouse.getDepots().size());
        assertSame(depot1.size(),warehouse.getDepots().get(0).getResources().size());
        assertEquals(depot1,warehouse.getDepots().get(0).getResources());
        assertSame(depot2.size(),warehouse.getDepots().get(1).getResources().size());
        assertEquals(depot2,warehouse.getDepots().get(1).getResources());
        assertSame(depot3.size(),warehouse.getDepots().get(2).getResources().size());
        assertEquals(depot3,warehouse.getDepots().get(2).getResources());
        assertSame(depot4.size(),warehouse.getDepots().get(3).getResources().size());
        assertEquals(depot4,warehouse.getDepots().get(3).getResources());
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 1 SERVANT
        //Depot 3 -> EMPTY
        //Depot 4 -> 1 SHIELD
    }

    @Test (expected = InvalidActionException.class)
    public void removeResource_WrongDepot_InvalidActionException() throws InvalidActionException {
        warehouse.removeResource(0);
    }

    @Test
    public void moveResourceToAbilityDepot() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        this.addResource();
        SpecialWarehouse specialWarehouse = new SpecialWarehouse(warehouse,servant);
        //Depot 5
        warehouse.moveResourceToAbilityDepot(2,5);
        depot2.remove(servant);
        depot5.add(servant);
        warehouse.addResource(servant,2);
        depot2.add(servant);
        assertSame(5,warehouse.getDepots().size());
        assertSame(depot1.size(),warehouse.getDepots().get(0).getResources().size());
        assertEquals(depot1,warehouse.getDepots().get(0).getResources());
        assertSame(depot2.size(),warehouse.getDepots().get(1).getResources().size());
        assertEquals(depot2,warehouse.getDepots().get(1).getResources());
        assertSame(depot3.size(),warehouse.getDepots().get(2).getResources().size());
        assertEquals(depot3,warehouse.getDepots().get(2).getResources());
        assertSame(depot4.size(),warehouse.getDepots().get(3).getResources().size());
        assertEquals(depot4,warehouse.getDepots().get(3).getResources());
        assertSame(depot5.size(),warehouse.getDepots().get(4).getResources().size());
        assertEquals(depot5,warehouse.getDepots().get(4).getResources());
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        //Depot 5 -> 1 SERVANT
    }

    @Test (expected = InvalidActionException.class)
    public void moveResourceToAbilityDepot_sameDepots_InvalidActionException() throws InvalidActionException {
        warehouse.moveResourceToAbilityDepot(3,3);
    }

    @Test (expected = InvalidActionException.class)
    public void moveResourceToAbilityDepot_NoAbilityDepot_InvalidActionException() throws InvalidActionException {
        warehouse.moveResourceToAbilityDepot(1,4);
    }

    @Test (expected = InvalidActionException.class)
    public void moveResourceToAbilityDepot_NotToAnAbilityDepot_InvalidActionException() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        this.addResource();
        warehouse.moveResourceToAbilityDepot(1,3);
    }

    @Test
    public void moveResources() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 1 SERVANT
        //Depot 3 -> EMPTY
        //Depot 4 -> 1 SHIELD
        this.removeResource();
        warehouse.moveResources(2,3);
        depot3.addAll(depot2);
        depot2.clear();
        warehouse.addResource(servant,3);
        warehouse.addResource(servant,3);
        depot3.add(servant);
        depot3.add(servant);
        warehouse.moveResources(1,4);
        depot4.addAll(depot1);
        depot1.clear();
        warehouse.moveResources(4,2);
        depot2.addAll(depot4);
        depot4.clear();
        assertSame(4,warehouse.getDepots().size());
        assertSame(depot1.size(),warehouse.getDepots().get(0).getResources().size());
        assertEquals(depot1,warehouse.getDepots().get(0).getResources());
        assertSame(depot2.size(),warehouse.getDepots().get(1).getResources().size());
        assertEquals(depot2,warehouse.getDepots().get(1).getResources());
        assertSame(depot3.size(),warehouse.getDepots().get(2).getResources().size());
        assertEquals(depot3,warehouse.getDepots().get(2).getResources());
        assertSame(depot4.size(),warehouse.getDepots().get(3).getResources().size());
        assertEquals(depot4,warehouse.getDepots().get(3).getResources());
        //Depot 1 -> EMPTY
        //Depot 2 -> 2 SHIELDS
        //Depot 3 -> 3 SERVANTS
        //Depot 4 -> EMPTY
    }

    @Test (expected = InvalidActionException.class)
    public void moveResources_sameDepots_InvalidActionException() throws InvalidActionException {
        warehouse.moveResources(3,3);
    }

    @Test (expected = InvalidActionException.class)
    public void moveResources_fromDepotWrong_InvalidActionException() throws InvalidActionException {
        warehouse.moveResources(0,3);
    }

    @Test (expected = InvalidActionException.class)
    public void moveResources_toDepotWrong_InvalidActionException() throws InvalidActionException {
        warehouse.moveResources(3,0);
    }

    @Test
    public void getDepots() throws InvalidActionException {
        ArrayList<Depot> depots = new ArrayList<>(warehouse.getDepots());
        assertSame(depots.size(),warehouse.getDepots().size());
        for(int i=0;i< warehouse.getDepots().size();i++) {
            assertEquals(depots.get(i),warehouse.getDepots().get(i));
            assertSame(depots.get(i).getResources().size(),warehouse.getDepots().get(i).getResources().size());
        }
        SpecialWarehouse specialWarehouse = new SpecialWarehouse(warehouse,stone);
        specialWarehouse = new SpecialWarehouse(warehouse,coin);
        depots.clear();
        depots.addAll(warehouse.getDepots());
        assertSame(depots.size(),warehouse.getDepots().size());
        for(int i=0;i< warehouse.getDepots().size();i++) {
            assertEquals(depots.get(i),warehouse.getDepots().get(i));
            assertSame(depots.get(i).getResources().size(),warehouse.getDepots().get(i).getResources().size());
        }
    }

    @Test
    public void checkAvailableDepot() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        this.addResource();
        assertTrue(warehouse.checkAvailableDepot(stone));
        assertFalse(warehouse.checkAvailableDepot(shield));
        assertFalse(warehouse.checkAvailableDepot(servant));
        assertFalse(warehouse.checkAvailableDepot(coin));
        //Depot 5
        SpecialWarehouse specialWarehouse = new SpecialWarehouse(warehouse,coin);
        assertTrue(warehouse.checkAvailableDepot(coin));
        assertTrue(warehouse.checkAvailableDepot(stone));
        assertFalse(warehouse.checkAvailableDepot(shield));
        assertFalse(warehouse.checkAvailableDepot(servant));
        warehouse.addResource(coin,5);
        assertTrue(warehouse.checkAvailableDepot(coin));
        assertTrue(warehouse.checkAvailableDepot(stone));
        assertFalse(warehouse.checkAvailableDepot(shield));
        assertFalse(warehouse.checkAvailableDepot(servant));
        warehouse.addResource(coin,5);
        assertFalse(warehouse.checkAvailableDepot(coin));
        assertTrue(warehouse.checkAvailableDepot(stone));
        assertFalse(warehouse.checkAvailableDepot(shield));
        assertFalse(warehouse.checkAvailableDepot(servant));
        warehouse.getDepots().get(2).getResources().clear();
        assertTrue(warehouse.checkAvailableDepot(coin));
        assertTrue(warehouse.checkAvailableDepot(stone));
        assertFalse(warehouse.checkAvailableDepot(shield));
        assertFalse(warehouse.checkAvailableDepot(servant));
    }

    @Test
    public void getContent() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        this.addResource();
        ArrayList<Resource> expectedContent = new ArrayList<>();
        expectedContent.addAll(depot1);
        expectedContent.addAll(depot2);
        expectedContent.addAll(depot3);
        expectedContent.addAll(depot4);
        assertEquals(expectedContent,warehouse.getContent());
    }

    @Test
    public void removeResources() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        this.addResource();
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(shield);
        resources.add(shield);
        resources.add(stone);
        resources.add(servant);
        warehouse.removeResources(resources);
        depot1.remove(shield);
        depot4.remove(shield);
        depot3.remove(stone);
        depot2.remove(servant);
        assertSame(4,warehouse.getDepots().size());
        assertSame(depot1.size(),warehouse.getDepots().get(0).getResources().size());
        assertEquals(depot1,warehouse.getDepots().get(0).getResources());
        assertSame(depot2.size(),warehouse.getDepots().get(1).getResources().size());
        assertEquals(depot2,warehouse.getDepots().get(1).getResources());
        assertSame(depot3.size(),warehouse.getDepots().get(2).getResources().size());
        assertEquals(depot3,warehouse.getDepots().get(2).getResources());
        assertSame(depot4.size(),warehouse.getDepots().get(3).getResources().size());
        assertEquals(depot4,warehouse.getDepots().get(3).getResources());
    }

    @Test (expected = InvalidActionException.class)
    public void removeResources_NotEnoughResources_InvalidActionException() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        this.addResource();
        ArrayList<Resource> toRemove=new ArrayList<>();
        toRemove.add(shield);
        toRemove.add(stone);
        toRemove.add(servant);
        toRemove.add(shield);
        toRemove.add(shield);
        toRemove.add(coin);
        warehouse.removeResources(toRemove);
    }

    @Test
    public void searchResource() throws InvalidActionException {
        //Depot 1 -> 1 SHIELD
        //Depot 2 -> 2 SERVANTS
        //Depot 3 -> 2 STONES
        //Depot 4 -> 2 SHIELDS
        this.addResource();
        assertSame(1,warehouse.searchResource(shield));
        assertSame(2,warehouse.searchResource(servant));
        assertSame(3,warehouse.searchResource(stone));
        assertSame(-1,warehouse.searchResource(coin));
    }

    @Test
    public void countResource() throws InvalidActionException {
        warehouse.addResource(new Resource(TypeResource.COIN),1);
        assertSame(1,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.COIN)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.STONE)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SHIELD)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SERVANT)));
        warehouse.addResource(new Resource(TypeResource.SHIELD),2);
        assertSame(1,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.COIN)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.STONE)));
        assertSame(1,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SHIELD)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SERVANT)));
        warehouse.addResource(new Resource(TypeResource.SHIELD),2);
        assertSame(1,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.COIN)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.STONE)));
        assertSame(2,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SHIELD)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SERVANT)));
        warehouse.addResource(new Resource(TypeResource.STONE),3);
        assertSame(1,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.COIN)));
        assertSame(1,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.STONE)));
        assertSame(2,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SHIELD)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SERVANT)));
        SpecialWarehouse specialWarehouse = new SpecialWarehouse(warehouse,new Resource(TypeResource.STONE));
        warehouse.addResource(new Resource(TypeResource.STONE),4);
        assertSame(1,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.COIN)));
        assertSame(2,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.STONE)));
        assertSame(2,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SHIELD)));
        assertSame(0,warehouse.countResource(warehouse.getContent(), new Resource(TypeResource.SERVANT)));
    }

    @Test
    public void checkEnoughResources() throws InvalidActionException {
        countResource();
        /*
        * 1 COIN
        * 2 SHIELD
        * 1 STONE
        * 1 STONE
        * */
        ArrayList<Resource> resourcesToCheck = new ArrayList<>();
        resourcesToCheck.add(new Resource(TypeResource.COIN));
        assertTrue(warehouse.checkEnoughResources(resourcesToCheck));
        resourcesToCheck.add(new Resource(TypeResource.SHIELD));
        assertTrue(warehouse.checkEnoughResources(resourcesToCheck));
        resourcesToCheck.add(new Resource(TypeResource.STONE));
        assertTrue(warehouse.checkEnoughResources(resourcesToCheck));
        resourcesToCheck.add(new Resource(TypeResource.STONE));
        assertTrue(warehouse.checkEnoughResources(resourcesToCheck));
        resourcesToCheck.add(new Resource(TypeResource.SERVANT));
        assertFalse(warehouse.checkEnoughResources(resourcesToCheck));
        resourcesToCheck.remove(resourcesToCheck.size()-1);
        assertTrue(warehouse.checkEnoughResources(resourcesToCheck));
        resourcesToCheck.add(new Resource(TypeResource.STONE));
        assertFalse(warehouse.checkEnoughResources(resourcesToCheck));
    }

    @Test
    public void getInstanceContent() throws InvalidActionException {
        countResource();
        /*
         * 1 COIN
         * 2 SHIELD
         * 1 STONE
         * 1 STONE
         * */
        HashMap<Integer,ArrayList<TypeResource>> expected = new HashMap<>();
        ArrayList<TypeResource> type1 = new ArrayList<>();
        type1.add(TypeResource.COIN);
        expected.put(1,type1);
        ArrayList<TypeResource> type2 = new ArrayList<>();
        type2.add(TypeResource.SHIELD);
        type2.add(TypeResource.SHIELD);
        expected.put(2,type2);
        ArrayList<TypeResource> type3 = new ArrayList<>();
        type3.add(TypeResource.STONE);
        expected.put(3,type3);
        ArrayList<TypeResource> type4 = new ArrayList<>();
        type4.add(TypeResource.STONE);
        expected.put(4,type4);
        assertEquals(expected,warehouse.getInstanceContent());
    }
}