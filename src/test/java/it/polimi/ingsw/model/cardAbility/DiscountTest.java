package it.polimi.ingsw.model.cardAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.BuyDiscount;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscountTest {
    Discount discount;

    @Before
    public void setUp() throws Exception {
        discount = new Discount(new Resource(Color.YELLOW));
    }

    @After
    public void tearDown() throws Exception {
        discount = null;
    }

    @Test
    public void activeAbility() throws InvalidActionException {
        Player player = new Player("username");
        assertFalse(discount.isActive());
        assertEquals("Buy",player.getBuyCard().toString());
        discount.activeAbility(player);
        assertTrue(discount.isActive());
        assertEquals("BuyDiscount",player.getBuyCard().toString());
        assertSame(1,((BuyDiscount) player.getBuyCard()).getResourceWithDiscount().size());
        Discount discount1 = new Discount(new Resource(Color.BLUE));
        assertFalse(discount1.isActive());
        discount1.activeAbility(player);
        assertTrue(discount1.isActive());
        assertEquals("BuyDiscount",player.getBuyCard().toString());
        assertSame(2,((BuyDiscount) player.getBuyCard()).getResourceWithDiscount().size());
    }

    @Test
    public void testToString() {
        assertEquals("Discount",discount.toString());
    }
}