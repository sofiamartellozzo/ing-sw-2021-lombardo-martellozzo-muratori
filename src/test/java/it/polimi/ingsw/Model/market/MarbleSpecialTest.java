package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MarbleSpecialTest extends TestCase {

    MarbleSpecial ms = null;

    @Before
    public void setUp() throws Exception {
        ms = new MarbleSpecial();
    }

    @After
    public void tearDown() throws Exception {
        ms = null;
    }

    @Test
    public void testGetColor(){
        Color color = ms.getColor();
        assertEquals(color, Color.WHITE);
    }

    public void testChoose() {
    }
}