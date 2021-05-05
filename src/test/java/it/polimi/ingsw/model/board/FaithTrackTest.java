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

        GoldBox box = new GoldBox(1,0,9);
        GoldBox box1 = new GoldBox(1,1,2);
        GoldBox box2 = new GoldBox(1,2,1);
        PopeBox box4 = new PopeBox(1,3);
        box4.setVictoryPoints(box2);
        box4.setActivated(true);
        PopeBox pop = new PopeBox(1,4);
        pop.setVictoryPoints(box2);
        pop.setActivated(false);
        GoldBox box5 = new GoldBox(2,5,3);

        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(0,box);
        boxes.add(1,box1);
        boxes.add(2,box2);
        boxes.add(3,box4);
        boxes.add(4,pop);
        boxes.add(5,box5);

        PopesFavorTile popesFavorTile1 = new PopesFavorTile(1,9);
        popesFavorTile1.setState(new Active());
        PopesFavorTile popesFavorTile2 = new PopesFavorTile(2,1);
        popesFavorTile2.setState(new Inactive());
        PopesFavorTile popesFavorTile3 = new PopesFavorTile(3,6);
        popesFavorTile3.setState(new Inactive());

        ArrayList<PopesFavorTile> popesFavorTiles = new ArrayList<>();
        popesFavorTiles.add(0,popesFavorTile1);
        popesFavorTiles.add(1,popesFavorTile2);
        popesFavorTiles.add(2,popesFavorTile3);

        ArrayList<Box> b = new ArrayList<>();
        b.add(boxes.get(0));
        b.add(boxes.get(1));
        b.add(boxes.get(2));
        b.add(boxes.get(3));
        PopesFavorTile popes = new PopesFavorTile(1,3);

        VaticanSection vaticanSection = new VaticanSection(1,b,popes);
        VaticanSection vaticanSection1 = new VaticanSection(2,b,popes);

        ArrayList<VaticanSection> vatSec = new ArrayList<>();
        vatSec.add(vaticanSection);
        vatSec.add(vaticanSection1);

        faithTrack = new FaithTrack(boxes,popesFavorTiles,new FaithMarker(),vatSec);

    }

    @After
    public void tearDown() throws Exception {
        faithTrack = null;
    }

    @Test
    public void testGetPopesFavorTiles() {

        ArrayList<PopesFavorTile> p = new ArrayList<>();
        p = faithTrack.getPopesFavorTiles();
        assertEquals(p.size(),3);
    }

    @Test
    public void testGetAllVictoryPoints() {

        faithTrack.setFaithMarker();
        int j = faithTrack.getAllVictoryPoints();

        assertEquals(j,10);

    }

    @Test
    public void testIncreasePosition() {

        faithTrack.getFaithMarker().increasePosition();
        assertEquals(faithTrack.getPositionFaithMarker(),1);
    }

    @Test
    public void testCheckInvokeVaticanReport() {

        faithTrack.setFaithMarker();
        int num = faithTrack.checkInvokeVaticanReport();
        assertEquals(num,1);

    }

    @Test
    public void testDoVaticanReport(){

        faithTrack.setFaithMarker();
        faithTrack.doVaticanReport(1);


    }
}