package it.polimi.ingsw.controller.factory;

import it.polimi.ingsw.model.board.FaithTrack;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

public class FaithTrackFactoryTest extends TestCase {

    FaithTrackFactory faithTrackFactory = null;

    @Before
    public void setUp() throws Exception {
        faithTrackFactory = new FaithTrackFactory();
    }

    @After
    public void tearDown() throws Exception {
        faithTrackFactory = null;
    }

    @Test
    public void testCreateFaithTrack() {
        FaithTrack faithTrack = null;
        try {
            faithTrack =  faithTrackFactory.createFaithTrack();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(25, faithTrack.getPathBox().size());
        assertEquals(3, faithTrack.getPopeBoxes().size());
        assertEquals(3, faithTrack.getPopesFavorTiles().size());
        assertEquals(0, faithTrack.getPositionFaithMarker());
        assertEquals(3, faithTrack.getVaticanSections().size());
    }
}