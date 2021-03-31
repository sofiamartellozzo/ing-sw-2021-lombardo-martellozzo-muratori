package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColoredMarbleTest extends TestCase {

    ColoredMarble cm = null;

    //Before is used for the method called whenever the class is instanziated
    @Before
    public void setUp() throws Exception {
        cm = new ColoredMarble(Color.YELLOW);
    }

    //every time I finish the test use @After to create the method that make null our object, to clean the space
    @After
    public void tearDown() throws Exception {
        cm = null;
    }

    //a generic test to see if a method worked
    @Test
    public void testGetColor(){
       Color color = cm.getColor();
       assertEquals(color, Color.YELLOW);
    }

    public void testChoose() {
    }
}