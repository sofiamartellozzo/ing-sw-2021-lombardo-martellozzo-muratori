package it.polimi.ingsw.Model.board.resourceManagement;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.TypeResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AbilityDepotTest {
    private AbilityDepot shieldDepot;
    private Resource shieldResource;
    private Resource coinResource;

    @Before
    public void setUp() throws Exception {
        shieldDepot= new AbilityDepot(TypeResource.SHIELD,1);
        shieldResource = new Resource(Color.BLUE,TypeResource.SHIELD);
        coinResource = new Resource(Color.YELLOW,TypeResource.COIN);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getResources_() {

    }

    @Test
    public void putResource_InsertingTwoShieldResources_CorrectOutputNoException() throws InvalidActionException {
        ArrayList<Resource> expectedOutput = new ArrayList<>();
        expectedOutput.add(shieldResource);
        expectedOutput.add(shieldResource);
        shieldDepot.putResource(shieldResource);
        shieldDepot.putResource(shieldResource);
        ArrayList<Resource> output = shieldDepot.getResources();
        assertEquals(expectedOutput,output);
    }

    @Test
    public void removeResource() {
    }
}