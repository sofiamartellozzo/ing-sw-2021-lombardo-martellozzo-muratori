package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RedMarbleTest extends TestCase {

    RedMarble rm = null;

    @Before
    public void setUp() throws Exception {
        rm = new RedMarble();
    }

    @After
    public void tearDown() throws Exception {
        rm = null;
    }

    @Test
    public void testGetColor(){
        Color color = rm.getColor();
        assertEquals(color, Color.RED);
    }

    public void testChoose() {
    }
}