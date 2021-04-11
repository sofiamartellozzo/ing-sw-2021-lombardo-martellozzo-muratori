package it.polimi.ingsw.Model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class VaticanSectionTest {
    VaticanSection vaticanSection;
    ArrayList<Box> boxes = new ArrayList<>();
    Box box;
    PopesFavorTile popesFavorTile;

    @Before
    public void setUp() throws Exception {
        for(int i=0;i<5;i++){
            box=new SimpleBox(1,i+1);
            boxes.add(box);
        }
        popesFavorTile= new PopesFavorTile(1,15);
        vaticanSection= new VaticanSection(1,boxes,popesFavorTile);
    }

    @After
    public void tearDown() throws Exception {
        vaticanSection=null;
    }

    @Test
    public void getWhichVaticanSection() {
        assertSame(1,vaticanSection.getWhichVaticanSection());
        vaticanSection = new VaticanSection(2,boxes,popesFavorTile);
        assertSame(2,vaticanSection.getWhichVaticanSection());
        vaticanSection = new VaticanSection(3,boxes,popesFavorTile);
        assertSame(3,vaticanSection.getWhichVaticanSection());
    }

    @Test
    public void getBoxes() {
        assertEquals(boxes,vaticanSection.getBoxes());
    }

    @Test
    public void getPopesFavorTile() {
        assertEquals(popesFavorTile,vaticanSection.getPopesFavorTile());
    }
}