package it.polimi.ingsw.Model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleBoxTest {
    SimpleBox simpleBox;
    @Before
    public void setUp() throws Exception {
        simpleBox=new SimpleBox(1,1);
        assertNotNull(simpleBox);
    }

    @After
    public void tearDown() throws Exception {
        simpleBox=null;
        assertNull(simpleBox);
    }

    @Test
    public void setVictoryPoints() {
        GoldBox goldBox = new GoldBox(1,1,24);
        simpleBox.setVictoryPoints(goldBox);
        assertSame(24,simpleBox.getVictoryPoints());
    }

    @Test
    public void getVictoryPoints() {
        simpleBox = new SimpleBox(1,12);
        assertSame(0,simpleBox.getVictoryPoints());
    }

    @Test
    public void getWhichSection() {
        simpleBox= new SimpleBox(3,12);
        assertSame(3,simpleBox.getWhichSection());
    }

    @Test
    public void getNumberBox() {
        simpleBox=new SimpleBox(1,13);
        assertSame(13,simpleBox.getNumberBox());
    }

    @Test
    public void testToString() {
        simpleBox=new SimpleBox(1,1);
        assertEquals("SimpleBox",simpleBox.toString());
    }
}