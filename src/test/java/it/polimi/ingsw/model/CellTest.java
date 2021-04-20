package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CellTest extends TestCase {

    Cell cell = null;

    @Before
    public void setUp() throws Exception {

        cell = new Cell(TypeResource.COIN);
    }

    @After
    public void tearDown() throws Exception {
        cell = null;
    }

    @Test
    public void testGetResources() {
        assertEquals(cell.getResources().getColor(),Color.YELLOW);
    }

    @Test
    public void testGetType() {
        assertEquals(cell.getType(),TypeResource.COIN);
    }

    @Test
    public void testAskResource() {

        assertEquals(cell.askResource().getType(),TypeResource.COIN);
    }
}