package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;


//Methods "FromWarehouse/StrongBox" and "ToWarehouse/StrongBox"
//not tested because they've been tested in the appropriate tests.

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
    public void TestGetStrongBox() {
        assertEquals(strongBox,resourceManager.getStrongBox());
    }

    @Test
    public void TestGetWarehouse() {
        assertEquals(warehouse,resourceManager.getWarehouse());
    }

    @Test
    public void TestSetWarehouse(){
        Warehouse warehouse = new WarehouseStandard();
        resourceManager.setWarehouse(warehouse);
        assertSame(warehouse,resourceManager.getWarehouse());}

    @Test
    public void TestSetStrongBox(){
        StrongBox strongBox = new StrongBox();
        resourceManager.setStrongBox(strongBox);
        assertSame(strongBox,resourceManager.getStrongBox());}

    @Test
    public void TestGetVictoryPoints() throws InvalidActionException {
        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        Resource stone = new Resource(Color.GREY);
        Resource servant = new Resource(Color.PURPLE);
        Resource servant1 = new Resource(Color.PURPLE);

        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(coin);
        resources.add(shield);
        resources.add(stone);
        resources.add(servant);
        resources.add(servant1);

        strongBox.addResources(resources);

        assertEquals(resourceManager.getVictoryPoints(),1);

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
    public void removeResourcesFromBoth() throws InvalidActionException {
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

        resourceManager.removeResourcesFromBoth(resources);

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

        Resource shield = new Resource(Color.BLUE);

        ArrayList<Resource> resources = new ArrayList<>();

        resources.add(shield);
        resources.add(shield);
        resources.add(shield);

        resourceManager.removeResourcesFromBoth(resources);
    }


    @Test
    public void countResource() throws InvalidActionException {
        Random randomNumber = new Random();
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        int shield=0;
        int stone=0;
        int servant=0;
        int coin=0;
        int n = randomNumber.nextInt(100);
        for(int i=0;i<n;i++){
            int r = randomNumber.nextInt(4);
            if(r==0){
                resourcesToAdd.add(new Resource(TypeResource.STONE));
                stone++;
            }else if(r==1){
                resourcesToAdd.add(new Resource(TypeResource.SHIELD));
                shield++;
            }else if(r==2){
                resourcesToAdd.add(new Resource(TypeResource.SERVANT));
                servant++;
            }else if(r==3){
                resourcesToAdd.add(new Resource(TypeResource.COIN));
                coin++;
            }
        }
        try {
            resourceManager.getStrongBox().addResources(resourcesToAdd);
        }catch(InvalidActionException e){
            e.printStackTrace();
        }
        assertSame(stone,resourceManager.countResource(resourceManager.getStrongBox().getContent(),new Resource(TypeResource.STONE)));
        assertSame(shield,resourceManager.countResource(resourceManager.getStrongBox().getContent(),new Resource(TypeResource.SHIELD)));
        assertSame(coin,resourceManager.countResource(resourceManager.getStrongBox().getContent(),new Resource(TypeResource.COIN)));
        assertSame(servant,resourceManager.countResource(resourceManager.getStrongBox().getContent(),new Resource(TypeResource.SERVANT)));
    }

    @Test
    public void TestGetContent() throws InvalidActionException {
        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        Resource stone = new Resource(Color.GREY);
        Resource servant = new Resource(Color.PURPLE);
        Resource servant1 = new Resource(Color.PURPLE);

        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(coin);
        resources.add(shield);
        resources.add(stone);
        resources.add(servant);
        resources.add(servant1);

        strongBox.addResources(resources);

        Resource coin1 = new Resource(Color.YELLOW);
        Resource coin2 = new Resource(Color.YELLOW);
        Resource coin3 = new Resource(Color.YELLOW);

        warehouse.addResource(coin1,3);
        warehouse.addResource(coin2,3);
        warehouse.addResource(coin3,3);

        assertEquals(resourceManager.getContent().size(),8);
    }

    @Test
    public void TestAddResourcesToStrongbox() throws InvalidActionException {
        Resource coin3 = new Resource(Color.YELLOW);
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(coin3);

        strongBox.addResources(resources);
        assertEquals(strongBox.getContent().size(),1);

    }

    @Test
    public void TestRemoveResourcesFromStrongBox() throws InvalidActionException {
        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        Resource stone = new Resource(Color.GREY);
        Resource servant = new Resource(Color.PURPLE);

        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(coin);
        resources.add(shield);
        resources.add(stone);
        resources.add(servant);

        Resource servant11 = new Resource(Color.PURPLE);

        ArrayList<Resource> removedResources = new ArrayList<>();
        removedResources.add(servant11);


        strongBox.addResources(resources);
        strongBox.removeResources(removedResources);

        assertSame(strongBox.getContent().get(2).getType(),TypeResource.STONE);
    }

    @Test
    public void TestAddResourceToWarehouse() throws InvalidActionException {
        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        Resource stone = new Resource(Color.GREY);
        Resource servant = new Resource(Color.PURPLE);

       warehouse.addResource(coin,1);
       warehouse.addResource(stone,2);
       warehouse.addResource(servant,3);

       assertEquals(warehouse.depots.get(0).getType(),TypeResource.COIN);
       assertEquals(warehouse.depots.get(1).getType(),TypeResource.STONE);
       assertEquals(warehouse.depots.get(2).getType(),TypeResource.SERVANT);


    }

}