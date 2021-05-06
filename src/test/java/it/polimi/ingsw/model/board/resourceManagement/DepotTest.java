package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DepotTest {
    Depot realDepot;
    Depot abilityDepot;

    @Before
    public void setUp() throws Exception {
        realDepot=new RealDepot(3,3);
        abilityDepot=new AbilityDepot(TypeResource.COIN,4);
    }

    @After
    public void tearDown() throws Exception {
        realDepot=null;
        abilityDepot=null;
    }

    @Test
    public void getResources() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Shields in realDepot and 2 Coins in the abilityDepot with the "addResource" method
        for(int i=0;i<2;i++){
            Resource shield = new Resource(Color.BLUE);
            realDepot.addResource(shield);
            expectedReal.add(shield);
        }
        for(int i=0;i<2;i++){
            Resource coin = new Resource(Color.YELLOW);
            abilityDepot.addResource(coin);
            expectedSpecial.add(coin);
        }
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 1 Shield from realDepot and 1 Coin from the abilityDepot with "removeResource" method
        realDepot.removeResource();
        expectedReal.remove(expectedReal.size()-1);
        abilityDepot.removeResource();
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Shields to realDepot with "addResources" method
        Resource shield = new Resource(Color.BLUE);
        realDepot.addResources(2,shield);
        expectedReal.add(shield);
        expectedReal.add(shield);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing all resources from both the depots
        realDepot.removeResources(3);
        for(int i=0;i<3;i++){
            expectedReal.remove(expectedReal.size()-1);
        }
        abilityDepot.removeResources(1);
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }

    @Test
    public void addResources_CorrectInput_NoException() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Shields to realDepot with "addResources" method
        Resource shield = new Resource(Color.BLUE);
        realDepot.addResources(2,shield);
        expectedReal.add(shield);
        expectedReal.add(shield);
        //Adding 1 coin to abilityDepot with "addResources" method
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResources(1,coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }

    @Test (expected = InvalidActionException.class)
    public void addResources_IncorrectInput_InvalidActionException() throws InvalidActionException {
        realDepot.addResources(1,null);
    }

    @Test (expected = InvalidActionException.class)
    public void addResources_DifferentType_InvalidActionException() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Shields to realDepot with "addResources" method
        Resource shield = new Resource(Color.BLUE);
        Resource coin = new Resource(Color.YELLOW);
        realDepot.addResources(1,shield);
        realDepot.addResources(1,coin);
        //Throw exception
        expectedReal.add(shield);
        expectedReal.add(shield);
    }

    @Test (expected = InvalidActionException.class)
    public void addResources_TooManyResources_InvalidActionException() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 3 Shields to realDepot with "addResources" method
        Resource shield = new Resource(Color.BLUE);
        realDepot.addResources(2,shield);
        //Adding 1 Shield to realDepot with "addResources" method
        shield = new Resource(Color.BLUE);
        realDepot.addResources(2,shield);
    }

    @Test
    public void removeResources() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 3 Shields to realDepot with "addResources" method
        Resource shield = new Resource(Color.BLUE);
        realDepot.addResources(3,shield);
        expectedReal.add(shield);
        expectedReal.add(shield);
        expectedReal.add(shield);
        //Adding 2 coin to abilityDepot with "addResources" method
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResources(2,coin);
        expectedSpecial.add(coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 2 shields and 1 coin from the depots with "removeResources" method
        realDepot.removeResources(2);
        for(int i=0;i<2;i++){
            expectedReal.remove(expectedReal.size()-1);
        }
        abilityDepot.removeResources(1);
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }

    @Test (expected = InvalidActionException.class)
    public void removeResources_EmptyDepot_InvalidActionException() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        realDepot.removeResources(2);
    }

    @Test (expected = InvalidActionException.class)
    public void removeResources_TooManyResourcesToRemove_InvalidActionException() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 3 Shields to realDepot with "addResources" method
        Resource shield = new Resource(Color.BLUE);
        realDepot.addResources(3,shield);
        expectedReal.add(shield);
        expectedReal.add(shield);
        expectedReal.add(shield);
        //Adding 2 coin to abilityDepot with "addResources" method
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResources(2,coin);
        expectedSpecial.add(coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 4 shields from the depots with "removeResources" method
        realDepot.removeResources(4);
    }

    @Test
    public void addResource() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Add 3 shield to realDepot and 2 coin to abilityDepot with "addResource"
        Resource shield= new Resource(Color.BLUE);
        realDepot.addResource(shield);
        expectedReal.add(shield);
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResource(coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        shield= new Resource(Color.BLUE);
        realDepot.addResource(shield);
        expectedReal.add(shield);
        coin = new Resource(Color.YELLOW);
        abilityDepot.addResource(coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        shield= new Resource(Color.BLUE);
        realDepot.addResource(shield);
        expectedReal.add(shield);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }


    @Test (expected = InvalidActionException.class)
    public void addResource_IncorrectInput_InvalidActionException() throws InvalidActionException {
        realDepot.addResource(null);
    }

    @Test (expected = InvalidActionException.class)
    public void addResource_FullDepot_InvalidActionException() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Add 4 shield to realDepot with "addResource"
        Resource shield= new Resource(Color.BLUE);
        realDepot.addResource(shield);
        expectedReal.add(shield);
        shield= new Resource(Color.BLUE);
        realDepot.addResource(shield);
        expectedReal.add(shield);
        shield= new Resource(Color.BLUE);
        realDepot.addResource(shield);
        expectedReal.add(shield);
        shield= new Resource(Color.BLUE);
        realDepot.addResource(shield);
        expectedReal.add(shield);
    }

    @Test (expected = InvalidActionException.class)
    public void addResource_DifferentResources_InvalidActionException() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Add 4 shield to realDepot with "addResource"
        Resource shield= new Resource(Color.BLUE);
        realDepot.addResource(shield);
        expectedReal.add(shield);
        Resource coin = new Resource(Color.YELLOW);
        realDepot.addResource(coin);
        expectedReal.add(shield);
    }

    @Test
    public void removeResource() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Shields in realDepot and 2 Coins in the abilityDepot with the "addResource" method
        for(int i=0;i<2;i++){
            Resource shield = new Resource(Color.BLUE);
            realDepot.addResource(shield);
            expectedReal.add(shield);
        }
        for(int i=0;i<2;i++){
            Resource coin = new Resource(Color.YELLOW);
            abilityDepot.addResource(coin);
            expectedSpecial.add(coin);
        }
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 1 Shield from realDepot and 1 Coin from the abilityDepot with "removeResource" method
        realDepot.removeResource();
        expectedReal.remove(expectedReal.size()-1);
        abilityDepot.removeResource();
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }

    @Test (expected = InvalidActionException.class)
    public void removeResource_EmptyDepot_InvalidActionException() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Shields in realDepot and 2 Coins in the abilityDepot with the "addResource" method
        for(int i=0;i<2;i++){
            Resource shield = new Resource(Color.BLUE);
            realDepot.addResource(shield);
            expectedReal.add(shield);
        }
        for(int i=0;i<2;i++){
            Resource coin = new Resource(Color.YELLOW);
            abilityDepot.addResource(coin);
            expectedSpecial.add(coin);
        }
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 1 Shield from realDepot and 1 Coin from the abilityDepot with "removeResource" method
        realDepot.removeResource();
        expectedReal.remove(expectedReal.size()-1);
        abilityDepot.removeResource();
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing another 2 shield from realDepot with "removeResource" method
        realDepot.removeResource();
        realDepot.removeResource();

    }

    @Test
    public void isFull() throws InvalidActionException {
        //Expected Result for RealDepot
        ArrayList<Resource> expectedReal = new ArrayList<>();
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(realDepot.getResources());
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedReal,realDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedReal.size(),realDepot.getResources().size());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        assertFalse(realDepot.isFull());
        //Adding 2 Shields in realDepot and 2 Coins in the abilityDepot
        for(int i=0;i<2;i++){
            Resource shield = new Resource(Color.BLUE);
            realDepot.addResource(shield);
            expectedReal.add(shield);
            assertFalse(realDepot.isFull());
        }
        abilityDepot.addResources(2,new Resource(Color.YELLOW));
        assertTrue(abilityDepot.isFull());
        realDepot.addResource(new Resource(Color.BLUE));
        assertTrue(realDepot.isFull());
    }

    @Test
    public void getType() throws InvalidActionException {
        assertEquals(TypeResource.COIN,abilityDepot.getType());
        Resource coin = new Resource(Color.YELLOW);
        Resource shield = new Resource(Color.BLUE);
        assertNull(realDepot.getType());
        realDepot.addResource(coin);
        assertEquals(TypeResource.COIN,realDepot.getType());
        realDepot.removeResources(realDepot.getResources().size());
        realDepot.addResources(3,shield);
        assertEquals(TypeResource.SHIELD,realDepot.getType());
    }

    @Test
    public void getSize() {
        assertSame(2,abilityDepot.getSize());
        for(int i=1;i<=3;i++){
            realDepot=new RealDepot(i,i);
            assertSame(i,realDepot.getSize());
        }
    }

    @Test
    public void getFloor(){
        assertSame(3,realDepot.getFloor());
        assertSame(4,abilityDepot.getFloor());
    }
}