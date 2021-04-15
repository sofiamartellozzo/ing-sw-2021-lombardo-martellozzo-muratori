package it.polimi.ingsw.model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PopeBoxTest {
    PopeBox popeBox;

    @Before
    public void setUp() throws Exception {
        popeBox=new PopeBox(1,1);
        assertNotNull(popeBox);
    }

    @After
    public void tearDown() throws Exception {
        popeBox=null;
        assertNull(popeBox);
    }

    @Test
    public void isLast() {
        popeBox=new PopeBox(1,1);
        assertFalse(popeBox.isLast());
        popeBox.setLast(true);
        assertTrue(popeBox.isLast());
    }

    @Test
    public void setLast() {
        popeBox=new PopeBox(1,1);
        assertFalse(popeBox.isLast());
        popeBox.setLast(true);
        assertTrue(popeBox.isLast());
    }

    @Test
    public void setVictoryPoints() {
        popeBox= new PopeBox(1,1);
        GoldBox goldBox= new GoldBox(1,35,14);
        popeBox.setVictoryPoints(goldBox);
        assertSame(14,popeBox.getVictoryPoints());
    }

    @Test
    public void getVictoryPoints() {
        popeBox= new PopeBox(1,1);
        assertSame(0,popeBox.getVictoryPoints());
    }

    @Test
    public void getWhichSection() {
        popeBox= new PopeBox(3,1);
        assertSame(3,popeBox.getWhichSection());
    }

    @Test
    public void getNumberBox() {
        popeBox= new PopeBox(1,22);
        assertSame(22,popeBox.getNumberBox());
    }

    @Test
    public void testToString() {
        popeBox= new PopeBox(1,1);
        assertEquals("PopeBox",popeBox.toString());
    }

    @Test
    public void isActivated() {
        popeBox=new PopeBox(1,1);
        assertFalse(popeBox.isActivated());
        popeBox.setActivated(true);
        assertTrue(popeBox.isActivated());
    }

    @Test
    public void setActivated() {
        popeBox=new PopeBox(1,1);
        assertFalse(popeBox.isActivated());
        popeBox.setActivated(true);
        assertTrue(popeBox.isActivated());
    }
}