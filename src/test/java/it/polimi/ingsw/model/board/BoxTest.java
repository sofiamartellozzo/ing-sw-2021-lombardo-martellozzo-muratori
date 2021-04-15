package it.polimi.ingsw.model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {
    Box box;
    @Before
    public void setUp() throws Exception {
        box = new SimpleBox(1,1);
        assertNotNull(box);
    }

    @After
    public void tearDown() throws Exception {
        box=null;
        assertNull(box);
    }

    @Test
    public void getWhichSection() {
        box=new SimpleBox(1,2);
        assertSame(1,box.getWhichSection());
    }

    @Test
    public void getNumberBox() {
        box=new GoldBox(1,24,1);
        assertSame(24,box.getNumberBox());
    }

    @Test
    public void getVictoryPoints() {
        box=new PopeBox(1,4);
        assertSame(0,box.getVictoryPoints());
    }

    @Test
    public void testToString() {
        box=new SimpleBox(1,1);
        assertEquals("SimpleBox",box.toString());
        box=new GoldBox(1,1,1);
        assertEquals("GoldBox",box.toString());
        box=new PopeBox(1,1);
        assertEquals("PopeBox",box.toString());
    }
}