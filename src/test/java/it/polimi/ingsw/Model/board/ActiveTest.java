package it.polimi.ingsw.Model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.RandomAccess;

import static org.junit.Assert.*;

public class ActiveTest {
    Active active;

    @Before
    public void setUp() throws Exception {
        active=new Active();
        assertNotNull(active);
    }

    @After
    public void tearDown() throws Exception {
        active=null;
        assertNull(active);
    }

    @Test
    public void returnPoints() {
        int victoryPoints=17;
        assertSame(17,active.returnPoints(victoryPoints));
    }

    @Test
    public void testToString() {
        active=new Active();
        assertEquals("Active",active.toString());
    }
}