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

public class AbilityDepotTest {
    AbilityDepot abilityDepot;

    @Before
    public void setUp() throws Exception {
        abilityDepot=new AbilityDepot(TypeResource.COIN,4);
    }

    @After
    public void tearDown() throws Exception {
        abilityDepot=null;
    }

    @Test
    public void getResources() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Coins in the abilityDepot with the "addResource" method
        for(int i=0;i<2;i++){
            Resource coin = new Resource(Color.YELLOW);
            abilityDepot.addResource(coin);
            expectedSpecial.add(coin);
        }
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 1 Coin from the abilityDepot with "removeResource" method
        abilityDepot.removeResource();
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing all resources from the depots
        abilityDepot.removeResources(1);
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }

    @Test
    public void addResources_CorrectInput_NoException() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 1 coin to abilityDepot with "addResources" method
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResources(1,coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }


    @Test (expected = InvalidActionException.class)
    public void addResources_IncorrectInput_NoException() throws InvalidActionException {
        abilityDepot.addResources(1,null);
    }

    @Test (expected = InvalidActionException.class)
    public void addResources_DifferentType_InvalidActionException() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 1 Shields to abilityDepot with "addResources" method
        Resource shield = new Resource(Color.BLUE);
        abilityDepot.addResources(1,shield);
    }

    @Test (expected = InvalidActionException.class)
    public void addResources_TooManyResources_InvalidActionException() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 3 Shields to realDepot with "addResources" method
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResources(3,coin);
    }

    @Test
    public void removeResources() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 coin to abilityDepot with "addResources" method
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResources(2,coin);
        expectedSpecial.add(coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 1 coin from the depots with "removeResources" method
        abilityDepot.removeResources(1);
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }

    @Test (expected = InvalidActionException.class)
    public void removeResources_EmptyDepot_InvalidActionException() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        abilityDepot.removeResources(2);
    }

    @Test (expected = InvalidActionException.class)
    public void removeResources_TooManyResourcesToRemove_InvalidActionException() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 coin to abilityDepot with "addResources" method
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResources(2,coin);
        expectedSpecial.add(coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 4 shields from the depots with "removeResources" method
        abilityDepot.removeResources(4);
    }

    @Test
    public void addResource() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Add 2 coin to abilityDepot with "addResource"
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResource(coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        coin = new Resource(Color.YELLOW);
        abilityDepot.addResource(coin);
        expectedSpecial.add(coin);
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }

    @Test (expected = InvalidActionException.class)
    public void addResource_IncorrectInput_NoException() throws InvalidActionException {
        abilityDepot.addResource(null);
    }

    @Test (expected = InvalidActionException.class)
    public void addResource_FullDepot_InvalidActionException() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Add 4 shield to realDepot with "addResource"
        Resource coin= new Resource(Color.YELLOW);
        abilityDepot.addResource(coin);
        expectedSpecial.add(coin);
        coin= new Resource(Color.YELLOW);
        abilityDepot.addResource(coin);
        expectedSpecial.add(coin);
        coin= new Resource(Color.YELLOW);
        abilityDepot.addResource(coin);
        expectedSpecial.add(coin);
    }

    @Test (expected = InvalidActionException.class)
    public void addResource_DifferentResources_InvalidActionException() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Add 4 shield to realDepot with "addResource"
        Resource shield= new Resource(Color.BLUE);
        abilityDepot.addResource(shield);
        expectedSpecial.add(shield);
        Resource coin = new Resource(Color.YELLOW);
        abilityDepot.addResource(coin);
        expectedSpecial.add(shield);
    }

    @Test
    public void removeResource() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Coins in the abilityDepot with the "addResource" method
        for(int i=0;i<2;i++){
            Resource coin = new Resource(Color.YELLOW);
            abilityDepot.addResource(coin);
            expectedSpecial.add(coin);
        }
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 1 Coin from the abilityDepot with "removeResource" method
        abilityDepot.removeResource();
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
    }

    @Test (expected = InvalidActionException.class)
    public void removeResource_EmptyDepot_InvalidActionException() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Adding 2 Coins in the abilityDepot with the "addResource" method
        for(int i=0;i<2;i++){
            Resource coin = new Resource(Color.YELLOW);
            abilityDepot.addResource(coin);
            expectedSpecial.add(coin);
        }
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing 2 Coin from the abilityDepot with "removeResource" method
        abilityDepot.removeResource();
        expectedSpecial.remove(expectedSpecial.size()-1);
        //Verifying if expected and effective depots are equals and contains the same number of resources
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        //Removing another 2 coin from realDepot with "removeResource" method
        abilityDepot.removeResource();
        expectedSpecial.remove(expectedSpecial.size()-1);
        abilityDepot.removeResource();
        expectedSpecial.remove(expectedSpecial.size()-1);

    }

    @Test
    public void isFull() throws InvalidActionException {
        //Expected Result for AbilityDepot
        ArrayList<Resource> expectedSpecial = new ArrayList<>();
        //Verifying if expected and effective depots are empty and not null
        assertNotNull(abilityDepot.getResources());
        assertEquals(expectedSpecial,abilityDepot.getResources());
        assertSame(expectedSpecial.size(),abilityDepot.getResources().size());
        assertFalse(abilityDepot.isFull());
        //Adding 2 Shields in realDepot and 2 Coins in the abilityDepot
        abilityDepot.addResources(2,new Resource(Color.YELLOW));
        assertTrue(abilityDepot.isFull());
    }

    @Test
    public void getType() {
        assertEquals(TypeResource.COIN,abilityDepot.getType());
    }

    @Test
    public void getSize() {
        assertSame(2,abilityDepot.getSize());
    }
}