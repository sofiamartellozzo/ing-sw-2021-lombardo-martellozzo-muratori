package it.polimi.ingsw.controller.factory;

import it.polimi.ingsw.model.ResourcesSupply;
import it.polimi.ingsw.model.TypeResource;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResourcesSupplyFactoryTest extends TestCase {

    ResourcesSupplyFactory resourcesSupplyFactory = null;

    @Before
    public void setUp() throws Exception {
        resourcesSupplyFactory = new ResourcesSupplyFactory();
    }

    @After
    public void tearDown() throws Exception {
        resourcesSupplyFactory = null;
    }

    @Test
    public void testCreateTheResourcesSupply() throws IllegalAccessException {
        ResourcesSupply rs = null;
        rs = resourcesSupplyFactory.createTheResourcesSupply();
        assertEquals(4, rs.getContent().size());
        assertEquals(TypeResource.COIN, rs.returnResourceAsked(TypeResource.COIN).getType());
    }
}