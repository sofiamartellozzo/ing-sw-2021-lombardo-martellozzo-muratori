package it.polimi.ingsw.model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoldBoxTest {
    GoldBox goldBox;
    @Before
    public void setUp() throws Exception {
        goldBox = new GoldBox(1,1,1);
        assertNotNull(goldBox);
    }

    @After
    public void tearDown() throws Exception {
        goldBox =null;
        assertNull(goldBox);
    }

    @Test
    public void getVictoryPoints() {
        goldBox = new GoldBox(1,1,45);
        assertSame(45,goldBox.getVictoryPoints());
    }

    @Test
    public void getWhichSection() {
        goldBox = new GoldBox(2,2,2);
        assertSame(2,goldBox.getWhichSection());
    }

    @Test
    public void getNumberBox() {
        goldBox = new GoldBox(3,23,45);
        assertSame(23,goldBox.getNumberBox());
    }

    @Test
    public void testToString() {
        goldBox = new GoldBox(1,1,1);
        assertEquals("GoldBox",goldBox.toString());
    }
}