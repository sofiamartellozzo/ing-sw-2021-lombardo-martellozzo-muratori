package it.polimi.ingsw.Model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StateTest {
    State state;

    @Before
    public void setUp() throws Exception {
        state=new Active();
        assertNotNull(state);
        state=new Inactive();
        assertNotNull(state);
    }

    @After
    public void tearDown() throws Exception {
        state=null;
        assertNull(state);
    }

    @Test
    public void returnPoints() {
        int victoryPoints=13;
        state=new Active();
        assertSame(13,state.returnPoints(victoryPoints));
        state=new Inactive();
        assertSame(0,state.returnPoints(victoryPoints));
    }

    @Test
    public void testToString() {
        state=new Active();
        assertEquals("Active",state.toString());
        state=new Inactive();
        assertEquals("Inactive",state.toString());
    }
}