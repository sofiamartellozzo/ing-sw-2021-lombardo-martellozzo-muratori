package it.polimi.ingsw.Model;

import junit.framework.TestCase;

public class ResourceTest extends TestCase {

    Resource resource = null;

    public void setUp() throws Exception {
        resource = new Resource(Color.BLUE);
    }

    public void tearDown() throws Exception {
        resource = null;
    }

    public void testGetColor() {
        Color color = resource.getColor();
        assertEquals(Color.BLUE, color);
    }

    public void testGetType() {
        TypeResource typeResource = resource.getType();
        assertEquals(TypeResource.SHIELD, typeResource);
    }
}