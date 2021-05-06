package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FaithMarkerTest extends TestCase {

    FaithMarker faithMarker = null;

    @Before
    public void setUp() throws Exception {
        faithMarker = new FaithMarker();
    }

    @After
    public void tearDown() throws Exception {
        faithMarker = null;
    }

    @Test
    public void testGetPosition_AtStart() {
        int i = faithMarker.getPosition();
        assertEquals(0,i);
    }

    @Test
    public void testGetPosition_afterIncreasing() {
        faithMarker.increasePosition();
        int i = faithMarker.getPosition();
        assertEquals(1,i);
    }

    @Test
    public void testSetPosition() {
        faithMarker.setPosition(3);
        int i = faithMarker.getPosition();
        assertEquals(3,i);
    }

    @Test
    public void testIncreasePosition() {
        faithMarker.setPosition(3);
        faithMarker.increasePosition();
        int i = faithMarker.getPosition();
        assertEquals(4,i);
    }

}