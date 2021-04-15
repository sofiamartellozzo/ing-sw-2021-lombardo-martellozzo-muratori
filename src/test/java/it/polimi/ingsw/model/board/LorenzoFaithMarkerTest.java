package it.polimi.ingsw.model.board;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LorenzoFaithMarkerTest extends TestCase {

    LorenzoFaithMarker lorenzoFaithMarker = null;

    @Before
    public void setUp() throws Exception {
        lorenzoFaithMarker = new LorenzoFaithMarker();
    }

    @After
    public void tearDown() throws Exception {
        lorenzoFaithMarker = null;
    }

    @Test
    public void testGetPosition() {
        int i = lorenzoFaithMarker.getPosition();
        assertEquals(0,i);
        lorenzoFaithMarker.increasePosition();
        i = lorenzoFaithMarker.getPosition();
        assertEquals(1,i);
    }

    @Test
    public void testSetPosition() {
        lorenzoFaithMarker.setPosition(3);
        int i = lorenzoFaithMarker.getPosition();
        assertEquals(3,i);
    }

    @Test
    public void testIncreasePosition() {
        lorenzoFaithMarker.setPosition(3);
        lorenzoFaithMarker.increasePosition();
        int i = lorenzoFaithMarker.getPosition();
        assertEquals(4,i);
    }
}