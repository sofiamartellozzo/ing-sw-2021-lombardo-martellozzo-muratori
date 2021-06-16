package it.polimi.ingsw.model.board;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SoloFaithTrackTest extends TestCase {

    SoloFaithTrack soloFaithTrack = null;

    @Before
    public void setUp() throws Exception {
        GoldBox box = new GoldBox(1,0,9);
        GoldBox box1 = new GoldBox(1,1,2);
        GoldBox box2 = new GoldBox(1,2,1);
        SimpleBox box4 = new SimpleBox(1,3);
        box4.setVictoryPoints(box2);
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
        b.add(new SimpleBox(1,2));
        b.add(new SimpleBox(1,3));
        b.add(new GoldBox(1,4,5));
        b.add(new PopeBox(1,4));
        PopesFavorTile popes = new PopesFavorTile(1,3);

        VaticanSection vaticanSection = new VaticanSection(1,b,popes);
        VaticanSection vaticanSection1 = new VaticanSection(2,b,popes);

        ArrayList<VaticanSection> vatSec = new ArrayList<>();
        vatSec.add(vaticanSection);
        vatSec.add(vaticanSection1);
        LorenzoFaithMarker lorenzoFaithMarker = new LorenzoFaithMarker();

        soloFaithTrack = new SoloFaithTrack(boxes,popesFavorTiles,new FaithMarker(),vatSec,lorenzoFaithMarker);
    }

    @After
    public void tearDown() throws Exception {
        soloFaithTrack = null;
    }

    @Test
    public void testIncreaseLorenzoPosition() {
        soloFaithTrack.increaseLorenzoPosition();
        soloFaithTrack.increaseLorenzoPosition();
        int num = soloFaithTrack.getLorenzoFaithMarker().getPosition();

        assertEquals(num, 2);
    }
}