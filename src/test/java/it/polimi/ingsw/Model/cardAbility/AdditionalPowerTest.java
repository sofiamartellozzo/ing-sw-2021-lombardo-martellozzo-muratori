package it.polimi.ingsw.Model.cardAbility;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdditionalPowerTest {
    AdditionalPower additionalPower;

    @Before
    public void setUp() throws Exception {
        additionalPower = new AdditionalPower(new Resource(Color.YELLOW));
    }

    @After
    public void tearDown() throws Exception {
        additionalPower=null;
    }

    @Test
    public void activeAbility() throws InvalidActionException {
        Player player = new Player("username");
        assertNull(player.getSpecialCard());
        assertFalse(additionalPower.isActive());
        additionalPower.activeAbility(player);
        assertTrue(additionalPower.isActive());
        assertNotNull(player.getSpecialCard());
        assertNotNull(player.getSpecialCard().get(0));
        assertSame(1,player.getSpecialCard().size());
        AdditionalPower additionalPower1 = new AdditionalPower(new Resource(Color.BLUE));
        assertFalse(additionalPower1.isActive());
        additionalPower1.activeAbility(player);
        assertTrue(additionalPower1.isActive());
        assertNotNull(player.getSpecialCard());
        assertNotNull(player.getSpecialCard().get(0));
        assertNotNull(player.getSpecialCard().get(1));
        assertSame(2,player.getSpecialCard().size());
    }

    @Test
    public void testToString() {
        assertEquals("AdditionalPower",additionalPower.toString());
    }
}