package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResourceTest extends TestCase {

    Resource resource = null;
    boolean thrown = false;

    @Before
    public void setUp() throws Exception {
        resource = new Resource(Color.BLUE);
    }

    @After
    public void tearDown() throws Exception {
        resource = null;
    }


    @Test
    public void testGetColor() {
        Color color = resource.getColor();
        assertEquals(Color.BLUE, color);
    }

    @Test
    public void testGetType() {
        TypeResource typeResource = resource.getType();
        assertEquals(TypeResource.SHIELD, typeResource);
    }
}