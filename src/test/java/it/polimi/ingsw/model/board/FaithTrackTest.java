package it.polimi.ingsw.model.board;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class FaithTrackTest extends TestCase {

    FaithTrack faithTrack = null;

    @Before
    public void setUp() throws Exception {

        Box box = new GoldBox(1,1,9);
        Box box1 = new GoldBox(1,2,1);
        Box box2 = new GoldBox(1,3,2);
        Box box3 = new GoldBox(1,4,0);

        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(0,box);
        boxes.add(1,box1);
        boxes.add(2,box2);
        boxes.add(3,box3);

        PopesFavorTile popesFavorTile1 = new PopesFavorTile(1,7);
        popesFavorTile1.setState(new Active());
        PopesFavorTile popesFavorTile2 = new PopesFavorTile(2,1);
        popesFavorTile2.setState(new Inactive());
        PopesFavorTile popesFavorTile3 = new PopesFavorTile(3,6);
        popesFavorTile3.setState(new Active());

        ArrayList<PopesFavorTile> popesFavorTiles = new ArrayList<>();
        popesFavorTiles.add(0,popesFavorTile1);
        popesFavorTiles.add(1,popesFavorTile2);
        popesFavorTiles.add(2,popesFavorTile3);

        ArrayList<Box> b = new ArrayList<>();
        b.add(new SimpleBox(1,2));
        b.add(new SimpleBox(1,3));
        PopesFavorTile pop = new PopesFavorTile(1,3);

        VaticanSection vaticanSection = new VaticanSection(1,b,pop);
        VaticanSection vaticanSection1 = new VaticanSection(2,b,pop);

        ArrayList<VaticanSection> vatSec = new ArrayList<>();

        faithTrack = new FaithTrack(boxes,popesFavorTiles,new FaithMarker(),vatSec);
    }

    @After
    public void tearDown() throws Exception {
        faithTrack = null;
    }

    /*@Test
    public void testGetPopesFavorTiles() {
        PopesFavorTile popesFavorTile = new PopesFavorTile(2,5);
        PopesFavorTile popesFavorTile1 = new PopesFavorTile(1,4);

        faithTrack.getPopesFavorTiles().add(popesFavorTile);
        faithTrack.getPopesFavorTiles().add(popesFavorTile1);

        ArrayList<PopesFavorTile> pope = faithTrack.getPopesFavorTiles();

        ArrayList<PopesFavorTile> p = new ArrayList<>();
        p.add(popesFavorTile);
        p.add(popesFavorTile1);

        assertEquals(pope,p);
    }*/

    @Test
    public void testGetAllVictoryPoints() {

        faithTrack.setFaithMarker();
        int j = faithTrack.getAllVictoryPoints();

        assertEquals(j,13);

    }

    @Test
    public void testIncreasePosition() {

        faithTrack.getFaithMarker().increasePosition();
        assertEquals(faithTrack.getPositionFaithMarker(),1);
    }

    @Test
    public void testCheckInvokeVaticanReport() {
    }

    @Test
    public void testDoVaticanReport(){

    }
}