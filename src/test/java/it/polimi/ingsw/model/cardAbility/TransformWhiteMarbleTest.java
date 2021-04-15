package it.polimi.ingsw.model.cardAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransformWhiteMarbleTest {
    TransformWhiteMarble transformWhiteMarble;

    @Before
    public void setUp() throws Exception {
        transformWhiteMarble = new TransformWhiteMarble(new Resource(Color.YELLOW));
    }

    @After
    public void tearDown() throws Exception {
        transformWhiteMarble=null;
    }

    @Test
    public void activeAbility() throws InvalidActionException {
        Player player = new Player("username");
        assertFalse(transformWhiteMarble.isActive());
        assertNull(player.getWhiteSpecialMarble());
        transformWhiteMarble.activeAbility(player);
        assertTrue(transformWhiteMarble.isActive());
        assertNotNull(player.getWhiteSpecialMarble());
        /*
        BISOGNA AGGIUNGER IL GET ABILITY
        assertNotNull(player.getWhiteSpecialMarble().getAbility());
        assertSame(1,player.getWhiteSpecialMarble().getAbility().size());
        for(int i=0;i<1;i++){
        assertNotNull(player.getWhiteSpecialMarble().getAbility().get(i));
        }
        */
        TransformWhiteMarble transformWhiteMarble1 = new TransformWhiteMarble(new Resource(Color.BLUE));
        assertFalse(transformWhiteMarble1.isActive());
        transformWhiteMarble1.activeAbility(player);
        assertTrue(transformWhiteMarble.isActive());
        assertNotNull(player.getWhiteSpecialMarble());
        /*
        BISOGNA AGGIUNGER IL GET ABILITY
        assertNotNull(player.getWhiteSpecialMarble().getAbility());
        assertSame(2,player.getWhiteSpecialMarble().getAbility().size());
        for(int i=0;i<2;i++){
        assertNotNull(player.getWhiteSpecialMarble().getAbility().get(i));
        }
        */
    }

    @Test
    public void testToString() {
        assertEquals("TransformWhiteMarble",transformWhiteMarble.toString());
    }
}