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
    public void getVictoryPoints() throws InvalidActionException {
        // Depot 1 -> 1 Shield
        // Depot 2 -> 2 Servants
        // Depot 3 -> EMPTY
        // Depot 4 -> 1 Shield
        // StrongBox -> 8 Coins, 8 Stones
        this.getResources();

        int victoryPoints = (1 + 2 + 1 + 8 + 8)/5;
        assertSame(victoryPoints,resourceManager.getVictoryPoints());
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

}