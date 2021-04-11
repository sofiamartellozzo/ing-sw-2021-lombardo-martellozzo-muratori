package it.polimi.ingsw.Model.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PopesFavorTileTest {
    PopesFavorTile popesFavorTile;

    @Before
    public void setUp() throws Exception {
        popesFavorTile = new PopesFavorTile(1,10);
    }

    @After
    public void tearDown() throws Exception {
        popesFavorTile=null;
    }

    @Test
    public void getWhichVaticanSection() {
        for(int i=1;i<=3;i++){
            popesFavorTile=new PopesFavorTile(i,10);
            assertSame(i,popesFavorTile.getWhichVaticanSection());
        }
    }

    @Test
    public void getVictoryPoints() {
        assertSame(0,popesFavorTile.getVictoryPoints());
        popesFavorTile.setState(new Active());
        assertSame(10,popesFavorTile.getVictoryPoints());
    }

    @Test
    public void getState() {
        State active = new Active();
        State inactive = new Inactive();
        popesFavorTile.setState(inactive);
        assertEquals(inactive,popesFavorTile.getState());
        popesFavorTile.setState(active);
        assertEquals(active,popesFavorTile.getState());
    }

    @Test
    public void setState() {
        State active = new Active();
        State inactive = new Inactive();
        popesFavorTile.setState(inactive);
        assertEquals(inactive,popesFavorTile.getState());
        popesFavorTile.setState(active);
        assertEquals(active,popesFavorTile.getState());
    }
}