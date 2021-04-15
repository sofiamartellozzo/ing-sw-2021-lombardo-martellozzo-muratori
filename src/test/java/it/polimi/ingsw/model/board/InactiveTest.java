package it.polimi.ingsw.model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InactiveTest {
    Inactive inactive;

    @Before
    public void setUp() throws Exception {
        inactive=new Inactive();
        assertNotNull(inactive);
    }

    @After
    public void tearDown() throws Exception {
        inactive=null;
        assertNull(inactive);
    }

    @Test
    public void returnPoints() {
        int victoryPoints=17;
        assertSame(0,inactive.returnPoints(victoryPoints));
    }

    @Test
    public void testToString() {
        inactive=new Inactive();
        assertEquals("Inactive",inactive.toString());
    }
}