package it.polimi.ingsw.model.board;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonalBoardTest extends TestCase {

    PersonalBoard personalBoard = null;

    @Before
    public void setUp() throws Exception {
       PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
       personalBoard = personalBoardFactory.createGame();
    }

    @After
    public void tearDown() throws Exception {
        personalBoard = null;
    }

    @Test
    public void testGetFaithTrack() {
        FaithTrack faithTrack = personalBoard.getFaithTrack();
        assertEquals(0, faithTrack.getPositionFaithMarker());
        assertEquals(3, faithTrack.getVaticanSections().size());
        assertEquals(3, faithTrack.getPopesFavorTiles().size());
        assertEquals(3, faithTrack.getPopeBoxes().size());
        assertEquals(25, faithTrack.getPathBox().size());
    }

    @Test
    public void testGetWarehouse() {
    }

    @Test
    public void testGetStrongbox() {
    }

    @Test
    public void testGetResourceManager() {
    }

    @Test
    public void testGetCardSpaces() {
    }

    @Test
    public void testGetCardSpace() {
    }

    @Test
    public void testGetAllCards() {
    }

    @Test
    public void testGetAllCardOfOneSpace() {
    }

    @Test
    public void testInvokeProductionPowerFromStrongBox() {
    }

    @Test
    public void testGetVictoryPointsFromCardSpaces() {
    }
}